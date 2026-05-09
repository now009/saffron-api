package com.saffron.qbank.service;

import com.saffron.qbank.dto.request.SessionStartRequest;
import com.saffron.qbank.dto.request.SubmitRequest;
import com.saffron.qbank.dto.response.ExamPaperResponse;
import com.saffron.qbank.dto.response.ExamSubjectResponse;
import com.saffron.qbank.dto.response.ExamTypeResponse;
import com.saffron.qbank.dto.response.SessionStartResponse;

import java.util.List;

public interface QbankExamService {
    List<ExamTypeResponse> findActiveTypes();
    List<ExamSubjectResponse> findActiveSubjects();
    List<ExamPaperResponse> findActivePapers(Integer typeId, Integer subjectId);
    SessionStartResponse startSession(SessionStartRequest request);
    void submitAnswers(Integer sessionId, SubmitRequest request);
}
