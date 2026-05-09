package com.saffron.qbank.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SessionStartResponse {
    private Integer id;
    private String examineeName;
    private Integer examPaperId;
    private List<QuestionExamResponse> questions;
}
