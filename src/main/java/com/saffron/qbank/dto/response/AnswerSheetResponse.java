package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.AnswerSheet;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AnswerSheetResponse {
    private final Integer id;
    private final Integer sessionId;
    private final Integer questionId;
    private final String answerText;
    private final Integer selectedChoice;
    private final Boolean isCorrect;
    private final LocalDateTime gradedAt;

    public AnswerSheetResponse(AnswerSheet a) {
        this.id = a.getId();
        this.sessionId = a.getSessionId();
        this.questionId = a.getQuestionId();
        this.answerText = a.getAnswerText();
        this.selectedChoice = a.getSelectedChoice();
        this.isCorrect = a.getIsCorrect();
        this.gradedAt = a.getGradedAt();
    }
}
