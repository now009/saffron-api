package com.saffron.qbank.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GradeRequest {
    private Boolean autoGrade = true;
    private List<ManualGrade> manualGrades;

    @Getter @Setter
    public static class ManualGrade {
        private Integer questionId;
        private Boolean isCorrect;
    }
}
