package com.saffron.qbank.dto.response;

import com.saffron.qbank.domain.Question;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionAdminResponse {
    private final Integer id;
    private final Integer examPaperId;
    private final Integer seq;
    private final String qType;
    private final String questionText;
    private final String imageUrl;
    private final Integer score;
    private final LocalDateTime createdAt;
    private final List<ChoiceAdminResponse> choices;

    public QuestionAdminResponse(Question q) {
        this.id = q.getId();
        this.examPaperId = q.getExamPaperId();
        this.seq = q.getSeq();
        this.qType = q.getQType();
        this.questionText = q.getQuestionText();
        this.imageUrl = q.getImageUrl();
        this.score = q.getScore();
        this.createdAt = q.getCreatedAt();
        this.choices = q.getChoices() == null ? List.of() :
            q.getChoices().stream().map(ChoiceAdminResponse::new).collect(Collectors.toList());
    }
}
