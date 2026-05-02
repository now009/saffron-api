package com.saffron.eai.common;

public class EaiAdapterException extends RuntimeException {

    public EaiAdapterException(String message) {
        super(message);
    }

    public EaiAdapterException(String message, Throwable cause) {
        super(message, cause);
    }
}
