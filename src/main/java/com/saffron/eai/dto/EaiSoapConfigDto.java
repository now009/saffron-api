package com.saffron.eai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EaiSoapConfigDto {

    private Long id;

    @NotBlank
    private String interfaceId;

    @NotBlank
    private String configName;

    @NotBlank
    private String wsdlUrl;

    @NotBlank
    private String serviceUrl;

    @NotBlank
    private String namespace;

    @NotBlank
    private String operationName;

    private String portName;
    private String soapVersion;
    private String soapAction;
    private Boolean mtomEnabled;

    @NotBlank
    private String wsSecurityType;

    private String wsUsername;
    private String wsPassword;
    private String wsPasswordType;
    private String keystorePath;
    private String keystorePassword;
    private Integer timeoutMs;
    private String requestTemplate;
    private String responsePath;
    private Boolean isActive;
}
