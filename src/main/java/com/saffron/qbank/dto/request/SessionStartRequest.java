package com.saffron.qbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionStartRequest {
    @NotNull
    private Integer examPaperId;
    @NotBlank
    private String examineeName;
    private String examineeNo;
}
