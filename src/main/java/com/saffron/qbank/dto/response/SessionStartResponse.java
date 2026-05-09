package com.saffron.qbank.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SessionStartResponse {
    private Integer sessionId;
    private String title;
    private Integer timeLimitMin;
    private List<QuestionExamResponse> questions;
}
