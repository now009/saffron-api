package com.saffron.qbank.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.saffron.qbank")
public class QbankExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<QbankResponse<Void>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(QbankResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<QbankResponse<Void>> handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(QbankResponse.fail(ex.getMessage()));
    }
}
