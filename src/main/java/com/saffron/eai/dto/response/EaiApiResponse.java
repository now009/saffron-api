package com.saffron.eai.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EaiApiResponse<T> {

    private String timestamp;
    private boolean success;
    private String message;
    private T data;

    public static <T> EaiApiResponse<T> ok(T data) {
        return new EaiApiResponse<>(LocalDateTime.now().toString(), true, "OK", data);
    }

    public static EaiApiResponse<Void> ok(String message) {
        return new EaiApiResponse<>(LocalDateTime.now().toString(), true, message, null);
    }

    public static EaiApiResponse<Void> fail(String message) {
        return new EaiApiResponse<>(LocalDateTime.now().toString(), false, message, null);
    }

    public static <T> EaiApiResponse<T> fail(String message, T data) {
        return new EaiApiResponse<>(LocalDateTime.now().toString(), false, message, data);
    }
}
