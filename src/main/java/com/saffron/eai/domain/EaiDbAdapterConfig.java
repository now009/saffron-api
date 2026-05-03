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
public class EaiDbAdapterConfig {

    private Long id;
    private String interfaceId;
    private String datasourceId;
    private String statementId;
    private String operationType;
    private String resultType;
    private String paramMapping;
    private Boolean rollbackOnError;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
