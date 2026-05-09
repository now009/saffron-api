package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.QuestionChoice;
import lombok.Getter;

@Getter
public class ChoiceExamResponse {
    private final Integer id;
    private final Integer seq;
    private final String choiceText;
    private final String imageUrl;

    public ChoiceExamResponse(QuestionChoice c) {
        this.id = c.getId();
        this.seq = c.getSeq();
        this.choiceText = c.getChoiceText();
        this.imageUrl = c.getImageUrl();
    }
}
