package com.saffron.eai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EaiRestConfig {

    private Long id;
    private String interfaceId;
    private String configName;
    private String url;
    private String httpMethod;
    private Integer timeoutMs;
    private String authType;
    private String authValue;
    private String apiKeyHeader;
    private String tokenUrl;
    private String clientId;
    private String clientSecret;
    private String tokenScope;
    private String contentType;
    private String requestHeaders;
    private String requestTemplate;
    private String successHttpCodes;
    private String responsePath;
    private Boolean sslVerify;
    private String proxyHost;
    private Integer proxyPort;
    private String proxyUser;
    private String proxyPassword;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
