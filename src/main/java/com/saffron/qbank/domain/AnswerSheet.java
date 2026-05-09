package com.saffron.qbank.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AnswerSheet {
    private Integer id;
    private Integer sessionId;
    private Integer questionId;
    private String answerText;
    private Integer selectedChoice;
    private Boolean isCorrect;
    private LocalDateTime gradedAt;
}
