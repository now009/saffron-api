package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.QuestionChoice;
import lombok.Getter;

@Getter
public class ChoiceAdminResponse {
    private final Integer id;
    private final Integer seq;
    private final String choiceText;
    private final String imageUrl;
    private final Boolean isCorrect;

    public ChoiceAdminResponse(QuestionChoice c) {
        this.id = c.getId();
        this.seq = c.getSeq();
        this.choiceText = c.getChoiceText();
        this.imageUrl = c.getImageUrl();
        this.isCorrect = c.getIsCorrect();
    }
}
