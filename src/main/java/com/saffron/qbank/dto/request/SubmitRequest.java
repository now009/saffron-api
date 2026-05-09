package com.saffron.qbank.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SubmitRequest {
    @NotNull
    private List<AnswerItem> answers;

    @Getter @Setter
    public static class AnswerItem {
        private Integer questionId;
        private Integer selectedChoiceId;
        private String answerText;
    }
}
