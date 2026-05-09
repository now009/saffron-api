package com.saffron.qbank.domain;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class QuestionChoice {
    private Integer id;
    private Integer questionId;
    private Integer seq;
    private String choiceText;
    private String imageUrl;
    private Boolean isCorrect;
}
