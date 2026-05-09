package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.ExamPaper;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExamPaperResponse {
    private final Integer id;
    private final Integer examTypeId;
    private final Integer examSubjectId;
    private final String title;
    private final Integer timeLimitMin;
    private final Boolean isActive;
    private final LocalDateTime createdAt;

    public ExamPaperResponse(ExamPaper e) {
        this.id = e.getId();
        this.examTypeId = e.getExamTypeId();
        this.examSubjectId = e.getExamSubjectId();
        this.title = e.getTitle();
        this.timeLimitMin = e.getTimeLimitMin();
        this.isActive = e.getIsActive();
        this.createdAt = e.getCreatedAt();
    }
}
