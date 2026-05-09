package com.saffron.qbank.service;

import com.saffron.qbank.domain.*;
import com.saffron.qbank.dto.request.SessionStartRequest;
import com.saffron.qbank.dto.request.SubmitRequest;
import com.saffron.qbank.dto.response.*;
import com.saffron.qbank.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QbankExamServiceImpl implements QbankExamService {

    private final ExamTypeMapper examTypeMapper;
    private final ExamSubjectMapper examSubjectMapper;
    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final QuestionChoiceMapper questionChoiceMapper;
    private final ExamSessionMapper examSessionMapper;
    private final AnswerSheetMapper answerSheetMapper;

    @Override
    public List<ExamTypeResponse> findActiveTypes() {
        return examTypeMapper.selectAll().stream()
            .map(ExamTypeResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<ExamSubjectResponse> findActiveSubjects() {
        return examSubjectMapper.selectAll().stream()
            .map(ExamSubjectResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<ExamPaperResponse> findActivePapers(Integer typeId, Integer subjectId) {
        return examPaperMapper.selectAll(typeId, subjectId, true).stream()
            .map(ExamPaperResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SessionStartResponse startSession(SessionStartRequest request) {
        ExamPaper paper = examPaperMapper.selectById(request.getExamPaperId());
        if (paper == null || !Boolean.TRUE.equals(paper.getIsActive())) {
            throw new RuntimeException("활성화된 시험지를 찾을 수 없습니다: " + request.getExamPaperId());
        }

        ExamSession session = ExamSession.builder()
            .examPaperId(paper.getId())
            .examineeName(request.getExamineeName())
            .examineeNo(request.getExamineeNo())
            .build();
        examSessionMapper.insert(session);

        // 문항 + 보기 조회 (is_correct 제외)
        List<Question> questions = questionMapper.selectByPaperId(paper.getId());
        questions.forEach(q -> q.setChoices(questionChoiceMapper.selectByQuestionId(q.getId())));

        List<QuestionExamResponse> questionResponses = questions.stream()
            .map(QuestionExamResponse::new)
            .collect(Collectors.toList());

        return SessionStartResponse.builder()
            .id(session.getId())
            .examineeName(session.getExamineeName())
            .examPaperId(paper.getId())
            .questions(questionResponses)
            .build();
    }

    @Override
    @Transactional
    public void submitAnswers(Integer sessionId, SubmitRequest request) {
        ExamSession session = examSessionMapper.selectById(sessionId);
        if (session == null) throw new RuntimeException("응시 세션을 찾을 수 없습니다: " + sessionId);
        if (session.getSubmittedAt() != null) throw new RuntimeException("이미 제출된 세션입니다: " + sessionId);

        for (SubmitRequest.AnswerItem item : request.getAnswers()) {
            AnswerSheet answer = AnswerSheet.builder()
                .sessionId(sessionId)
                .questionId(item.getQuestionId())
                .selectedChoice(item.getSelectedChoiceId())
                .answerText(item.getAnswerText())
                .build();
            answerSheetMapper.insert(answer);
        }

        examSessionMapper.updateSubmitted(sessionId);
    }
}
