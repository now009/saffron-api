package com.saffron.qbank.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDetailResponse {
    private Integer questionId;
    private Integer seq;
    private String qType;
    private String questionText;
    private String selectedChoiceText;
    private String answerText;
    private Boolean isCorrect;
}
