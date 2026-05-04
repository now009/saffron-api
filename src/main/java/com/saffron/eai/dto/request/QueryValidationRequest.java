package com.saffron.eai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryValidationRequest {

    @NotBlank
    private String datasourceId;

    @NotBlank
    private String query;
}
