package com.saffron.qbank.controller;

import com.saffron.qbank.dto.request.*;
import com.saffron.qbank.dto.response.*;
import com.saffron.qbank.service.QbankAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qbank/admin")
@RequiredArgsConstructor
public class QbankAdminController {

    private final QbankAdminService adminService;

    // ── 시험종류 ──────────────────────────────────────────────

    @GetMapping("/exam-types")
    public ResponseEntity<List<ExamTypeResponse>> getExamTypes() {
        return ResponseEntity.ok(adminService.findAllTypes());
    }

    @PostMapping("/exam-types")
    public ResponseEntity<ExamTypeResponse> createExamType(@RequestBody @Valid ExamTypeRequest req) {
        return ResponseEntity.ok(adminService.createType(req));
    }

    @PutMapping("/exam-types/{id}")
    public ResponseEntity<ExamTypeResponse> updateExamType(@PathVariable Integer id,
                                                            @RequestBody @Valid ExamTypeRequest req) {
        return ResponseEntity.ok(adminService.updateType(id, req));
    }

    @DeleteMapping("/exam-types/{id}")
    public ResponseEntity<Void> deleteExamType(@PathVariable Integer id) {
        adminService.deleteType(id);
        return ResponseEntity.ok().build();
    }

    // ── 시험대상 ──────────────────────────────────────────────

    @GetMapping("/exam-subjects")
    public ResponseEntity<List<ExamSubjectResponse>> getExamSubjects() {
        return ResponseEntity.ok(adminService.findAllSubjects());
    }

    @PostMapping("/exam-subjects")
    public ResponseEntity<ExamSubjectResponse> createExamSubject(@RequestBody @Valid ExamSubjectRequest req) {
        return ResponseEntity.ok(adminService.createSubject(req));
    }

    @PutMapping("/exam-subjects/{id}")
    public ResponseEntity<ExamSubjectResponse> updateExamSubject(@PathVariable Integer id,
                                                                   @RequestBody @Valid ExamSubjectRequest req) {
        return ResponseEntity.ok(adminService.updateSubject(id, req));
    }

    @DeleteMapping("/exam-subjects/{id}")
    public ResponseEntity<Void> deleteExamSubject(@PathVariable Integer id) {
        adminService.deleteSubject(id);
        return ResponseEntity.ok().build();
    }

    // ── 시험지 ────────────────────────────────────────────────

    @GetMapping("/papers")
    public ResponseEntity<List<ExamPaperResponse>> getPapers() {
        return ResponseEntity.ok(adminService.findAllPapers());
    }

    @GetMapping("/papers/{id}")
    public ResponseEntity<ExamPaperResponse> getPaper(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.findPaperById(id));
    }

    @PostMapping("/papers")
    public ResponseEntity<ExamPaperResponse> createPaper(@RequestBody @Valid ExamPaperRequest req) {
        return ResponseEntity.ok(adminService.createPaper(req));
    }

    @PutMapping("/papers/{id}")
    public ResponseEntity<ExamPaperResponse> updatePaper(@PathVariable Integer id,
                                                          @RequestBody @Valid ExamPaperRequest req) {
        return ResponseEntity.ok(adminService.updatePaper(id, req));
    }

    @DeleteMapping("/papers/{id}")
    public ResponseEntity<Void> deletePaper(@PathVariable Integer id) {
        adminService.deletePaper(id);
        return ResponseEntity.ok().build();
    }

    // ── 문항 ──────────────────────────────────────────────────

    @GetMapping("/papers/{paperId}/questions")
    public ResponseEntity<List<QuestionAdminResponse>> getQuestions(@PathVariable Integer paperId) {
        return ResponseEntity.ok(adminService.findQuestionsByPaper(paperId));
    }

    @PostMapping("/papers/{paperId}/questions")
    public ResponseEntity<QuestionAdminResponse> createQuestion(@PathVariable Integer paperId,
                                                                  @RequestBody @Valid QuestionRequest req) {
        return ResponseEntity.ok(adminService.createQuestion(paperId, req));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuestionAdminResponse> updateQuestion(@PathVariable Integer id,
                                                                  @RequestBody @Valid QuestionRequest req) {
        return ResponseEntity.ok(adminService.updateQuestion(id, req));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id) {
        adminService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }

    // ── 보기 ──────────────────────────────────────────────────

    @PostMapping("/questions/{id}/choices")
    public ResponseEntity<ChoiceAdminResponse> createChoice(@PathVariable Integer id,
                                                              @RequestBody @Valid QuestionChoiceRequest req) {
        return ResponseEntity.ok(adminService.createChoice(id, req));
    }

    @PutMapping("/choices/{id}")
    public ResponseEntity<ChoiceAdminResponse> updateChoice(@PathVariable Integer id,
                                                              @RequestBody @Valid QuestionChoiceRequest req) {
        return ResponseEntity.ok(adminService.updateChoice(id, req));
    }

    @DeleteMapping("/choices/{id}")
    public ResponseEntity<Void> deleteChoice(@PathVariable Integer id) {
        adminService.deleteChoice(id);
        return ResponseEntity.ok().build();
    }

    // ── 세션 / 채점 ───────────────────────────────────────────

    @GetMapping("/sessions")
    public ResponseEntity<List<SessionResponse>> getSessions(
            @RequestParam(required = false) Integer paperId,
            @RequestParam(required = false) String examineeName) {
        return ResponseEntity.ok(adminService.findAllSessions(paperId, examineeName));
    }

    @GetMapping("/sessions/{id}/answers")
    public ResponseEntity<List<AnswerSheetResponse>> getAnswers(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.findAnswersBySession(id));
    }

    @PostMapping("/sessions/{id}/grade")
    public ResponseEntity<Void> grade(@PathVariable Integer id,
                                       @RequestBody GradeRequest req) {
        adminService.grade(id, req);
        return ResponseEntity.ok().build();
    }
}
