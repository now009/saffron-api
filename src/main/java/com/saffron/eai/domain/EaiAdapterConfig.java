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
public class EaiAdapterConfig {

    private Long id;
    private String interfaceId;
    private String adapterType;
    private String url;
    private String httpMethod;
    private Integer timeoutMs;
    private String authType;
    private String authValue;
    private String requestHeaders;
    private String datasourceId;
    private String statementId;
    private String operationType;
    private String remoteHost;
    private Integer remotePort;
    private String remoteUser;
    private String remotePassword;
    private String remotePath;
    private String donePath;
    private String filePattern;
    private String fileEncoding;
    private String extraConfig;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
