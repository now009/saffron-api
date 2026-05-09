package com.saffron.qbank.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ExamType {
    private Integer id;
    private String name;
    private Boolean isSurvey;
    private LocalDateTime createdAt;
}
