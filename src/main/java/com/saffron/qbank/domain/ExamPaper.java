package com.saffron.qbank.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ExamPaper {
    private Integer id;
    private Integer examTypeId;
    private Integer examSubjectId;
    private String title;
    private Integer timeLimitMin;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
