package com.saffron.qbank.domain;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Question {
    private Integer id;
    private Integer examPaperId;
    private Integer seq;
    private String qType;
    private String questionText;
    private String imageFileName;
    private String imageUrl;
    private Integer score;
    private LocalDateTime createdAt;
    private List<QuestionChoice> choices;
}
