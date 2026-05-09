package com.saffron.qbank.service;

import com.saffron.qbank.dto.request.*;
import com.saffron.qbank.dto.response.*;

import java.util.List;

public interface QbankAdminService {

    // 시험종류
    List<ExamTypeResponse> findAllTypes();
    ExamTypeResponse createType(ExamTypeRequest request);
    ExamTypeResponse updateType(Integer id, ExamTypeRequest request);
    void deleteType(Integer id);

    // 시험대상
    List<ExamSubjectResponse> findAllSubjects();
    ExamSubjectResponse createSubject(ExamSubjectRequest request);
    ExamSubjectResponse updateSubject(Integer id, ExamSubjectRequest request);
    void deleteSubject(Integer id);

    // 시험지
    List<ExamPaperResponse> findAllPapers();
    ExamPaperResponse findPaperById(Integer id);
    ExamPaperResponse createPaper(ExamPaperRequest request);
    ExamPaperResponse updatePaper(Integer id, ExamPaperRequest request);
    void deletePaper(Integer id);

    // 문항
    List<QuestionAdminResponse> findQuestionsByPaper(Integer paperId);
    QuestionAdminResponse createQuestion(Integer paperId, QuestionRequest request);
    QuestionAdminResponse updateQuestion(Integer id, QuestionRequest request);
    void deleteQuestion(Integer id);

    // 보기
    ChoiceAdminResponse createChoice(Integer questionId, QuestionChoiceRequest request);
    ChoiceAdminResponse updateChoice(Integer id, QuestionChoiceRequest request);
    void deleteChoice(Integer id);

    // 세션
    List<SessionResponse> findAllSessions(Integer paperId, String examineeName);
    List<AnswerSheetResponse> findAnswersBySession(Integer sessionId);
    void grade(Integer sessionId, GradeRequest request);
}
