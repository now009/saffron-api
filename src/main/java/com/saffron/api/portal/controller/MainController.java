package com.saffron.api.portal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class MainController {

    @Value("${app.redirect.main-url}")
    private String mainRedirectUrl;

    @GetMapping("/main")
    public ResponseEntity<Void> main(
            @RequestParam(value = "access_token", required = false) String accessToken) {

        // 검증된 access_token을 프론트엔드로 전달
        String redirectUrl = (accessToken != null)
                ? mainRedirectUrl + "?access_token=" + accessToken
                : mainRedirectUrl;

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}
