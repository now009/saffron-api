package com.saffron.eai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EaiRestConfigDto {

    private Long id;

    @NotBlank
    private String interfaceId;

    @NotBlank
    private String configName;

    @NotBlank
    private String url;

    @NotBlank
    private String httpMethod;

    private Integer timeoutMs;

    @NotBlank
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
}
