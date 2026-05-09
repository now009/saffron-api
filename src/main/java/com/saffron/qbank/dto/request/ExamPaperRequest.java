package com.saffron.qbank.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExamPaperRequest {
    @NotNull
    private Integer examTypeId;
    @NotNull
    private Integer examSubjectId;
    private String title;
    private Integer timeLimitMin;
    private Boolean isActive = true;
}
