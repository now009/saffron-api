package com.saffron.portal.dto.common;

public class ApiResponse {

    private String messageCode;
    private String message;

    private ApiResponse(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public static ApiResponse success(String message) {
        return new ApiResponse("success", message);
    }

    public static ApiResponse fail(String message) {
        return new ApiResponse("fail", message);
    }

    public String getMessageCode() { return messageCode; }
    public String getMessage() { return message; }
}
