package com.saffron.qbank.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuestionChoiceRequest {
    @NotNull
    private Integer seq;
    private String choiceText;
    private String imageUrl;
    private Boolean isCorrect = false;
}
