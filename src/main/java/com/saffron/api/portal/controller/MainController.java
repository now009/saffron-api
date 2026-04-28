package com.saffron.api.portal.controller;

import com.saffron.api.portal.security.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Value("${app.redirect.main-url}")
    private String mainRedirectUrl;

    @Value("${app.redirect.login-url}")
    private String loginRedirectUrl;

    private final JwtTokenProvider jwtTokenProvider;

    public MainController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/main")
    public ResponseEntity<Void> main(
            @RequestParam(value = "access_token", required = false) String accessToken) {

        if (accessToken == null || accessToken.isBlank()) {
            log.warn("access_token 누락 → 로그인으로 리다이렉트");
            return redirect(loginRedirectUrl);
        }

        try {
            jwtTokenProvider.parse(accessToken);
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT 검증 실패: {}", e.getMessage());
            return redirect(loginRedirectUrl);
        }

        String encoded = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
        return redirect(mainRedirectUrl + "?access_token=" + encoded);
    }

    private ResponseEntity<Void> redirect(String url) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
