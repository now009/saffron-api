package com.saffron.qbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExamTypeRequest {
    @NotBlank
    private String name;
    private Boolean isSurvey = false;
}
