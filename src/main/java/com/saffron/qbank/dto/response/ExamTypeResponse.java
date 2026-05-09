package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.ExamType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExamTypeResponse {
    private final Integer id;
    private final String name;
    private final Boolean isSurvey;
    private final LocalDateTime createdAt;

    public ExamTypeResponse(ExamType e) {
        this.id = e.getId();
        this.name = e.getName();
        this.isSurvey = e.getIsSurvey();
        this.createdAt = e.getCreatedAt();
    }
}
