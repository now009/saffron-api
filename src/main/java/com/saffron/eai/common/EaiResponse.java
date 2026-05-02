package com.saffron.eai.common;

public class EaiResponse {

    private final String status;
    private final String body;
    private final long processingMs;

    private EaiResponse(String status, String body, long processingMs) {
        this.status = status;
        this.body = body;
        this.processingMs = processingMs;
    }

    public static EaiResponse success(String body, long processingMs) {
        return new EaiResponse("SUCCESS", body, processingMs);
    }

    public static EaiResponse fail(String message, long processingMs) {
        return new EaiResponse("FAIL", message, processingMs);
    }

    public String getStatus() { return status; }
    public String getBody() { return body; }
    public long getProcessingMs() { return processingMs; }

    public boolean isSuccess() { return "SUCCESS".equals(status); }
}
