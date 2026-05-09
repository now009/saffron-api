package com.saffron.qbank.common;

import lombok.Getter;

@Getter
public class QbankResponse<T> {

    private final boolean success;
    private final T data;
    private final String message;

    private QbankResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> QbankResponse<T> ok(T data) {
        return new QbankResponse<>(true, data, null);
    }

    public static <T> QbankResponse<T> fail(String message) {
        return new QbankResponse<>(false, null, message);
    }
}
