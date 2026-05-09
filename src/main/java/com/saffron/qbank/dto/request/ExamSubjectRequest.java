package com.saffron.qbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExamSubjectRequest {
    @NotBlank
    private String name;
    private String grade;
}
