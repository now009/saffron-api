package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.Question;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionExamResponse {
    private final Integer id;
    private final Integer seq;
    private final String qType;
    private final String questionText;
    private final String imageUrl;
    private final Integer score;
    private final List<ChoiceExamResponse> choices;

    public QuestionExamResponse(Question q) {
        this.id = q.getId();
        this.seq = q.getSeq();
        this.qType = q.getQType();
        this.questionText = q.getQuestionText();
        this.imageUrl = q.getImageUrl();
        this.score = q.getScore();
        this.choices = q.getChoices() == null ? List.of() :
            q.getChoices().stream().map(ChoiceExamResponse::new).collect(Collectors.toList());
    }
}
