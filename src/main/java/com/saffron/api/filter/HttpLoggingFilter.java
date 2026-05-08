package com.saffron.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ReadListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final int maxBodySize;
    private final boolean logStatusCheck;

    public HttpLoggingFilter(int maxBodySize, boolean logStatusCheck) {
        this.maxBodySize = maxBodySize;
        this.logStatusCheck = logStatusCheck;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // body 를 byte[] 로 미리 읽어둔다 (ContentCachingRequestWrapper 미사용)
        byte[] requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        CachedBodyRequest wrappedRequest = new CachedBodyRequest(request, requestBody);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // 요청 로그 (chain 진입 전)
        logRequest(wrappedRequest, requestBody);

        long start = System.currentTimeMillis();
        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            // 응답 로그 (chain 완료 후)
            logResponse(wrappedResponse, elapsed);
            // 클라이언트에 실제 응답이 흐르도록 반드시 복사
            wrappedResponse.copyBodyToResponse();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 필터 제외 대상 (현재는 없음)
        // 예) /api/ 로 시작하지 않는 경로(정적 리소스 등) 제외
        // String uri = request.getRequestURI();
        // if (!uri.startsWith("/api/")) return true;

        // 폴링 헬스체크 로그 끄기 옵션
        if (!logStatusCheck) {
            String uri = request.getRequestURI();
            if (uri != null && (uri.startsWith("/actuator/health") || uri.equals("/health"))) {
                return true;
            }
        }
        return false;
    }

    private void logRequest(HttpServletRequest request, byte[] body) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String contentType = request.getContentType();
        String fullUri = (query == null) ? uri : uri + "?" + query;
        String bodyStr = formatBody(body, contentType);

        if (bodyStr.isEmpty()) {
            log.info(">>> REQ  {} {}  [{}]", method, fullUri, contentType);
        } else {
            log.info(">>> REQ  {} {}  [{}]\n      {}", method, fullUri, contentType, bodyStr);
        }
    }

    private void logResponse(ContentCachingResponseWrapper response, long elapsedMs) {
        int status = response.getStatus();
        String contentType = response.getContentType();
        byte[] body = response.getContentAsByteArray();
        String bodyStr = formatBody(body, contentType);

        if (bodyStr.isEmpty()) {
            log.info("<<< RES  {} ({}ms)  [{}]", status, elapsedMs, contentType);
        } else {
            log.info("<<< RES  {} ({}ms)  [{}]\n      {}", status, elapsedMs, contentType, bodyStr);
        }
    }

    private String formatBody(byte[] body, String contentType) {
        if (body == null || body.length == 0) {
            return "";
        }
        if (isBinary(contentType)) {
            return "[binary " + body.length + " bytes]";
        }
        Charset cs = resolveCharset(contentType);
        if (body.length > maxBodySize) {
            String head = new String(body, 0, maxBodySize, cs);
            return head + " ...(truncated, total " + body.length + " bytes)";
        }
        return new String(body, cs);
    }

    private boolean isBinary(String contentType) {
        if (contentType == null) return false;
        String ct = contentType.toLowerCase();
        return ct.startsWith("image/")
                || ct.startsWith("audio/")
                || ct.startsWith("video/")
                || ct.contains("octet-stream")
                || ct.contains("pdf")
                || ct.contains("zip")
                || ct.startsWith("multipart/");
    }

    private Charset resolveCharset(String contentType) {
        if (contentType != null) {
            for (String part : contentType.split(";")) {
                String p = part.trim();
                if (p.toLowerCase().startsWith("charset=")) {
                    try {
                        return Charset.forName(p.substring("charset=".length()).trim());
                    } catch (Exception ignore) {
                        // fallthrough → UTF-8
                    }
                }
            }
        }
        return StandardCharsets.UTF_8;
    }

    /**
     * body 를 미리 읽어둔 byte[] 로 재포장하여 controller(@RequestBody)가
     * 다시 읽을 수 있게 해주는 request wrapper.
     */
    private static class CachedBodyRequest extends HttpServletRequestWrapper {
        private final byte[] body;

        CachedBodyRequest(HttpServletRequest request, byte[] body) {
            super(request);
            this.body = body;
        }

        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override public boolean isFinished() { return bais.available() == 0; }
                @Override public boolean isReady() { return true; }
                @Override public void setReadListener(ReadListener listener) { }
                @Override public int read() { return bais.read(); }
            };
        }

        @Override
        public BufferedReader getReader() {
            Charset cs = (getCharacterEncoding() != null)
                    ? Charset.forName(getCharacterEncoding())
                    : StandardCharsets.UTF_8;
            return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body), cs));
        }
    }
}
