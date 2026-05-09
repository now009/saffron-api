package com.saffron.qbank.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ExamSession {
    private Integer id;
    private Integer examPaperId;
    private String examineeName;
    private String examineeNo;
    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;
    private Integer totalScore;
}
