package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.ExamSubject;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExamSubjectResponse {
    private final Integer id;
    private final String name;
    private final String grade;
    private final LocalDateTime createdAt;

    public ExamSubjectResponse(ExamSubject e) {
        this.id = e.getId();
        this.name = e.getName();
        this.grade = e.getGrade();
        this.createdAt = e.getCreatedAt();
    }
}
