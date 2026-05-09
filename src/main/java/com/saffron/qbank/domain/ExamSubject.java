package com.saffron.qbank.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ExamSubject {
    private Integer id;
    private String name;
    private String grade;
    private LocalDateTime createdAt;
}
