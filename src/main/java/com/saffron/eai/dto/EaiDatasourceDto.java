package com.saffron.eai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EaiDatasourceDto {

    private Long id;

    @NotBlank
    private String datasourceId;

    @NotBlank
    private String datasourceName;

    @NotBlank
    private String dbType;

    @NotBlank
    private String jdbcUrl;

    @NotBlank
    private String dbUsername;

    private String dbPassword;

    @NotBlank
    private String driverClass;

    private Integer poolMinSize;
    private Integer poolMaxSize;
    private Integer poolTimeoutMs;
    private Integer queryTimeoutSec;
    private String defaultSchema;
    private String connectionProps;
    private Boolean isActive;
    private String description;
    private String createdBy;
}
