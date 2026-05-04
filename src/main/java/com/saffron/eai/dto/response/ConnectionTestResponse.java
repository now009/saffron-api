package com.saffron.eai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionTestResponse {

    private boolean success;
    private String message;

    public static ConnectionTestResponse ok(String message) {
        return new ConnectionTestResponse(true, message);
    }

    public static ConnectionTestResponse fail(String message) {
        return new ConnectionTestResponse(false, message);
    }
}
