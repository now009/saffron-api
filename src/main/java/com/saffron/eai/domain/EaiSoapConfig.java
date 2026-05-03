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
public class EaiSoapConfig {

    private Long id;
    private String interfaceId;
    private String configName;
    private String wsdlUrl;
    private String serviceUrl;
    private String namespace;
    private String operationName;
    private String portName;
    private String soapVersion;
    private String soapAction;
    private Boolean mtomEnabled;
    private String wsSecurityType;
    private String wsUsername;
    private String wsPassword;
    private String wsPasswordType;
    private String keystorePath;
    private String keystorePassword;
    private Integer timeoutMs;
    private String requestTemplate;
    private String responsePath;
    private LocalDateTime wsdlCachedAt;
    private String wsdlCache;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
