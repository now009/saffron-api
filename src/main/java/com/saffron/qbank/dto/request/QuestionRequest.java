package com.saffron.qbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuestionRequest {
    @NotNull
    private Integer seq;
    @NotBlank
    private String qType;
    @NotBlank
    private String questionText;
    private String imageUrl;
    private Integer score = 1;
}
