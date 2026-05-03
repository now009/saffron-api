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
public class EaiDatasource {

    private Long id;
    private String datasourceId;
    private String datasourceName;
    private String dbType;
    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
