package com.saffron.portal.dto.common;

public class CommonResponse<T> {

    private String code;
    private String message;
    private T data;

    private CommonResponse(String code, String message, T data) {
        this.code    = code;
        this.message = message;
        this.data    = data;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("200", "success", data);
    }

    public static <T> CommonResponse<T> notFound(String message) {
        return new CommonResponse<>("404", message, null);
    }

    public String getCode()    { return code; }
    public String getMessage() { return message; }
    public T getData()         { return data; }
}
