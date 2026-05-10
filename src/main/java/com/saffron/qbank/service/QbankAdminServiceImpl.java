package com.saffron.qbank.service;

import com.saffron.qbank.config.QbankImageStore;
import com.saffron.qbank.domain.*;
import com.saffron.qbank.dto.request.*;
import com.saffron.qbank.dto.response.*;
import com.saffron.qbank.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QbankAdminServiceImpl implements QbankAdminService {

    private final ExamTypeMapper examTypeMapper;
    private final ExamSubjectMapper examSubjectMapper;
    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final QuestionChoiceMapper questionChoiceMapper;
    private final ExamSessionMapper examSessionMapper;
    private final AnswerSheetMapper answerSheetMapper;
    private final QbankImageStore imageStore;

    // ── 시험종류 ──────────────────────────────────────────────

    @Override
    public List<ExamTypeResponse> findAllTypes() {
        return examTypeMapper.selectAll().stream()
            .map(ExamTypeResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExamTypeResponse createType(ExamTypeRequest req) {
        ExamType type = ExamType.builder()
            .name(req.getName())
            .isSurvey(Boolean.TRUE.equals(req.getIsSurvey()))
            .build();
        examTypeMapper.insert(type);
        return new ExamTypeResponse(examTypeMapper.selectById(type.getId()));
    }

    @Override
    @Transactional
    public ExamTypeResponse updateType(Integer id, ExamTypeRequest req) {
        ExamType type = requireType(id);
        type.setName(req.getName());
        type.setIsSurvey(Boolean.TRUE.equals(req.getIsSurvey()));
        examTypeMapper.update(type);
        return new ExamTypeResponse(examTypeMapper.selectById(id));
    }

    @Override
    @Transactional
    public void deleteType(Integer id) {
        requireType(id);
        examTypeMapper.deleteById(id);
    }

    // ── 시험대상 ──────────────────────────────────────────────

    @Override
    public List<ExamSubjectResponse> findAllSubjects() {
        return examSubjectMapper.selectAll().stream()
            .map(ExamSubjectResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExamSubjectResponse createSubject(ExamSubjectRequest req) {
        ExamSubject subject = ExamSubject.builder()
            .name(req.getName())
            .grade(req.getGrade())
            .build();
        examSubjectMapper.insert(subject);
        return new ExamSubjectResponse(examSubjectMapper.selectById(subject.getId()));
    }

    @Override
    @Transactional
    public ExamSubjectResponse updateSubject(Integer id, ExamSubjectRequest req) {
        ExamSubject subject = requireSubject(id);
        subject.setName(req.getName());
        subject.setGrade(req.getGrade());
        examSubjectMapper.update(subject);
        return new ExamSubjectResponse(examSubjectMapper.selectById(id));
    }

    @Override
    @Transactional
    public void deleteSubject(Integer id) {
        requireSubject(id);
        examSubjectMapper.deleteById(id);
    }

    // ── 시험지 ────────────────────────────────────────────────

    @Override
    public List<ExamPaperResponse> findAllPapers() {
        return examPaperMapper.selectAll(null, null, null).stream()
            .map(ExamPaperResponse::new).collect(Collectors.toList());
    }

    @Override
    public ExamPaperResponse findPaperById(Integer id) {
        return new ExamPaperResponse(requirePaper(id));
    }

    @Override
    @Transactional
    public ExamPaperResponse createPaper(ExamPaperRequest req) {
        ExamPaper paper = ExamPaper.builder()
            .examTypeId(req.getExamTypeId())
            .examSubjectId(req.getExamSubjectId())
            .title(req.getTitle())
            .timeLimitMin(req.getTimeLimitMin())
            .isActive(Boolean.TRUE.equals(req.getIsActive()))
            .build();
        examPaperMapper.insert(paper);
        return new ExamPaperResponse(examPaperMapper.selectById(paper.getId()));
    }

    @Override
    @Transactional
    public ExamPaperResponse updatePaper(Integer id, ExamPaperRequest req) {
        ExamPaper paper = requirePaper(id);
        paper.setExamTypeId(req.getExamTypeId());
        paper.setExamSubjectId(req.getExamSubjectId());
        paper.setTitle(req.getTitle());
        paper.setTimeLimitMin(req.getTimeLimitMin());
        paper.setIsActive(req.getIsActive());
        examPaperMapper.update(paper);
        return new ExamPaperResponse(examPaperMapper.selectById(id));
    }

    @Override
    @Transactional
    public void deletePaper(Integer id) {
        requirePaper(id);
        examPaperMapper.deleteById(id);
    }

    // ── 문항 ──────────────────────────────────────────────────

    @Override
    public List<QuestionAdminResponse> findQuestionsByPaper(Integer paperId) {
        List<Question> questions = questionMapper.selectByPaperId(paperId);
        questions.forEach(q -> q.setChoices(questionChoiceMapper.selectByQuestionId(q.getId())));
        return questions.stream().map(QuestionAdminResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionAdminResponse createQuestion(Integer paperId, QuestionRequest req) {
        String imageFileName = req.getImageFileName();
        Question question = Question.builder()
            .examPaperId(paperId)
            .seq(req.getSeq())
            .qType(req.getQType())
            .questionText(req.getQuestionText())
            .imageFileName(imageFileName)
            .imageUrl(imageStore.toFilePath(imageFileName))
            .score(req.getScore() != null ? req.getScore() : 1)
            .build();
        questionMapper.insert(question);
        Question saved = questionMapper.selectById(question.getId());
        saved.setChoices(questionChoiceMapper.selectByQuestionId(saved.getId()));
        return new QuestionAdminResponse(saved);
    }

    @Override
    @Transactional
    public QuestionAdminResponse updateQuestion(Integer id, QuestionRequest req) {
        Question question = requireQuestion(id);
        question.setSeq(req.getSeq());
        question.setQType(req.getQType());
        question.setQuestionText(req.getQuestionText());
        question.setScore(req.getScore() != null ? req.getScore() : 1);
        if (req.getImageFileName() != null) {
            question.setImageFileName(req.getImageFileName());
            question.setImageUrl(imageStore.toFilePath(req.getImageFileName()));
        }
        questionMapper.update(question);
        question.setChoices(questionChoiceMapper.selectByQuestionId(id));
        return new QuestionAdminResponse(question);
    }

    @Override
    @Transactional
    public void deleteQuestion(Integer id) {
        requireQuestion(id);
        questionMapper.deleteById(id);
    }

    // ── 보기 ──────────────────────────────────────────────────

    @Override
    @Transactional
    public ChoiceAdminResponse createChoice(Integer questionId, QuestionChoiceRequest req) {
        requireQuestion(questionId);
        QuestionChoice choice = QuestionChoice.builder()
            .questionId(questionId)
            .seq(req.getSeq())
            .choiceText(req.getChoiceText())
            .imageUrl(req.getImageUrl())
            .isCorrect(Boolean.TRUE.equals(req.getIsCorrect()))
            .build();
        questionChoiceMapper.insert(choice);
        return new ChoiceAdminResponse(questionChoiceMapper.selectById(choice.getId()));
    }

    @Override
    @Transactional
    public ChoiceAdminResponse updateChoice(Integer id, QuestionChoiceRequest req) {
        QuestionChoice choice = requireChoice(id);
        choice.setSeq(req.getSeq());
        choice.setChoiceText(req.getChoiceText());
        choice.setImageUrl(req.getImageUrl());
        choice.setIsCorrect(Boolean.TRUE.equals(req.getIsCorrect()));
        questionChoiceMapper.update(choice);
        return new ChoiceAdminResponse(questionChoiceMapper.selectById(id));
    }

    @Override
    @Transactional
    public void deleteChoice(Integer id) {
        requireChoice(id);
        questionChoiceMapper.deleteById(id);
    }

    // ── 세션 / 채점 ───────────────────────────────────────────

    @Override
    public List<SessionResponse> findAllSessions(Integer paperId, String examineeName) {
        return examSessionMapper.selectAll(paperId, examineeName).stream()
            .map(SessionResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<AnswerDetailResponse> findAnswersBySession(Integer sessionId) {
        return answerSheetMapper.selectDetailBySessionId(sessionId);
    }

    @Override
    @Transactional
    public void grade(Integer sessionId, GradeRequest request) {
        ExamSession session = requireSession(sessionId);
        List<AnswerSheet> answers = answerSheetMapper.selectBySessionId(sessionId);

        // 자동 채점 (single / multi)
        if (Boolean.TRUE.equals(request.getAutoGrade())) {
            autoGrade(answers);
        }

        // 수동 채점 (subjective)
        if (request.getManualGrades() != null) {
            Map<Integer, Boolean> manualMap = new HashMap<>();
            for (GradeRequest.ManualGrade mg : request.getManualGrades()) {
                manualMap.put(mg.getQuestionId(), mg.getIsCorrect());
            }
            for (AnswerSheet a : answers) {
                if (manualMap.containsKey(a.getQuestionId())) {
                    a.setIsCorrect(manualMap.get(a.getQuestionId()));
                    answerSheetMapper.updateGrade(a.getId(), a.getIsCorrect());
                }
            }
        }

        // 총점 계산 후 세션 업데이트
        int totalScore = calcTotalScore(session, answers);
        examSessionMapper.updateTotalScore(sessionId, totalScore);
    }

    // ── private helpers ───────────────────────────────────────

    private void autoGrade(List<AnswerSheet> answers) {
        // questionId별로 그룹화
        Map<Integer, List<AnswerSheet>> byQuestion = answers.stream()
            .filter(a -> a.getSelectedChoice() != null)
            .collect(Collectors.groupingBy(AnswerSheet::getQuestionId));

        for (Map.Entry<Integer, List<AnswerSheet>> entry : byQuestion.entrySet()) {
            Integer questionId = entry.getKey();
            List<AnswerSheet> qAnswers = entry.getValue();

            List<Integer> correctIds = questionChoiceMapper.selectCorrectChoiceIds(questionId);
            int totalCorrect = correctIds.size();
            Set<Integer> correctSet = new HashSet<>(correctIds);
            Set<Integer> selectedIds = qAnswers.stream()
                .map(AnswerSheet::getSelectedChoice)
                .collect(Collectors.toSet());

            // 모든 정답을 선택하고, 오답을 선택하지 않은 경우에만 correct
            boolean allCorrect = selectedIds.equals(correctSet);

            for (AnswerSheet a : qAnswers) {
                a.setIsCorrect(allCorrect);
                answerSheetMapper.updateGrade(a.getId(), allCorrect);
            }
        }
    }

    private int calcTotalScore(ExamSession session, List<AnswerSheet> answers) {
        // 문항 seq별 score를 조회하여 정답 합산
        List<Question> questions = questionMapper.selectByPaperId(session.getExamPaperId());
        Map<Integer, Integer> scoreMap = questions.stream()
            .collect(Collectors.toMap(Question::getId, Question::getScore));

        // questionId별 정답 여부 (중복 제거 - multi는 하나라도 correct=true면 점수 부여)
        Map<Integer, Boolean> correctByQuestion = new HashMap<>();
        for (AnswerSheet a : answers) {
            if (Boolean.TRUE.equals(a.getIsCorrect())) {
                correctByQuestion.put(a.getQuestionId(), true);
            } else {
                correctByQuestion.putIfAbsent(a.getQuestionId(), false);
            }
        }

        return correctByQuestion.entrySet().stream()
            .filter(Map.Entry::getValue)
            .mapToInt(e -> scoreMap.getOrDefault(e.getKey(), 0))
            .sum();
    }

    private ExamType requireType(Integer id) {
        ExamType t = examTypeMapper.selectById(id);
        if (t == null) throw new RuntimeException("시험종류를 찾을 수 없습니다: " + id);
        return t;
    }

    private ExamSubject requireSubject(Integer id) {
        ExamSubject s = examSubjectMapper.selectById(id);
        if (s == null) throw new RuntimeException("시험대상을 찾을 수 없습니다: " + id);
        return s;
    }

    private ExamPaper requirePaper(Integer id) {
        ExamPaper p = examPaperMapper.selectById(id);
        if (p == null) throw new RuntimeException("시험지를 찾을 수 없습니다: " + id);
        return p;
    }

    private Question requireQuestion(Integer id) {
        Question q = questionMapper.selectById(id);
        if (q == null) throw new RuntimeException("문항을 찾을 수 없습니다: " + id);
        return q;
    }

    private QuestionChoice requireChoice(Integer id) {
        QuestionChoice c = questionChoiceMapper.selectById(id);
        if (c == null) throw new RuntimeException("보기를 찾을 수 없습니다: " + id);
        return c;
    }

    private ExamSession requireSession(Integer id) {
        ExamSession s = examSessionMapper.selectById(id);
        if (s == null) throw new RuntimeException("응시 세션을 찾을 수 없습니다: " + id);
        return s;
    }
}
