package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.ExamSession;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SessionResponse {
    private final Integer id;
    private final Integer examPaperId;
    private final String examineeName;
    private final String examineeNo;
    private final LocalDateTime startedAt;
    private final LocalDateTime submittedAt;
    private final Integer totalScore;

    public SessionResponse(ExamSession e) {
        this.id = e.getId();
        this.examPaperId = e.getExamPaperId();
        this.examineeName = e.getExamineeName();
        this.examineeNo = e.getExamineeNo();
        this.startedAt = e.getStartedAt();
        this.submittedAt = e.getSubmittedAt();
        this.totalScore = e.getTotalScore();
    }
}
