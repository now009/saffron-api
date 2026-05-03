package com.saffron.eai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EaiDbAdapterConfigDto {

    private Long id;

    @NotBlank
    private String interfaceId;

    @NotBlank
    private String datasourceId;

    @NotBlank
    private String statementId;

    @NotBlank
    private String operationType;

    private String resultType;
    private String paramMapping;
    private Boolean rollbackOnError;
    private Boolean isActive;
}
