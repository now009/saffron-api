package com.saffron.qbank.controller;

import com.saffron.qbank.common.QbankResponse;
import com.saffron.qbank.config.QbankImageStore;
import com.saffron.qbank.dto.request.*;
import com.saffron.qbank.dto.response.*;
import com.saffron.qbank.service.QbankAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/qbank/admin")
@RequiredArgsConstructor
public class QbankAdminController {

    private final QbankAdminService adminService;
    private final QbankImageStore imageStore;

    // ── 이미지 업로드 ─────────────────────────────────────────

    @PostMapping("/images")
    public ResponseEntity<QbankResponse<ImageUploadResponse>> uploadImage(
            MultipartHttpServletRequest request) {
        MultipartFile file = request.getFileMap().values().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("업로드된 파일이 없습니다."));
        QbankImageStore.SaveResult result = imageStore.saveMultipart(file);
        return ResponseEntity.ok(QbankResponse.ok(
                new ImageUploadResponse(result.fileName(), result.imageUrl())));
    }

    // ── 시험종류 ──────────────────────────────────────────────

    @GetMapping("/exam-types")
    public ResponseEntity<QbankResponse<List<ExamTypeResponse>>> getExamTypes() {
        return ResponseEntity.ok(QbankResponse.ok(adminService.findAllTypes()));
    }

    @PostMapping("/exam-types")
    public ResponseEntity<QbankResponse<ExamTypeResponse>> createExamType(@RequestBody @Valid ExamTypeRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.createType(req)));
    }

    @PutMapping("/exam-types/{id}")
    public ResponseEntity<QbankResponse<ExamTypeResponse>> updateExamType(@PathVariable Integer id,
                                                                           @RequestBody @Valid ExamTypeRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.updateType(id, req)));
    }

    @DeleteMapping("/exam-types/{id}")
    public ResponseEntity<QbankResponse<Void>> deleteExamType(@PathVariable Integer id) {
        adminService.deleteType(id);
        return ResponseEntity.ok(QbankResponse.ok(null));
    }

    // ── 시험대상 ──────────────────────────────────────────────

    @GetMapping("/exam-subjects")
    public ResponseEntity<QbankResponse<List<ExamSubjectResponse>>> getExamSubjects() {
        return ResponseEntity.ok(QbankResponse.ok(adminService.findAllSubjects()));
    }

    @PostMapping("/exam-subjects")
    public ResponseEntity<QbankResponse<ExamSubjectResponse>> createExamSubject(@RequestBody @Valid ExamSubjectRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.createSubject(req)));
    }

    @PutMapping("/exam-subjects/{id}")
    public ResponseEntity<QbankResponse<ExamSubjectResponse>> updateExamSubject(@PathVariable Integer id,
                                                                                 @RequestBody @Valid ExamSubjectRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.updateSubject(id, req)));
    }

    @DeleteMapping("/exam-subjects/{id}")
    public ResponseEntity<QbankResponse<Void>> deleteExamSubject(@PathVariable Integer id) {
        adminService.deleteSubject(id);
        return ResponseEntity.ok(QbankResponse.ok(null));
    }

    // ── 시험지 ────────────────────────────────────────────────

    @GetMapping("/papers")
    public ResponseEntity<QbankResponse<List<ExamPaperResponse>>> getPapers() {
        return ResponseEntity.ok(QbankResponse.ok(adminService.findAllPapers()));
    }

    @GetMapping("/papers/{id}")
    public ResponseEntity<QbankResponse<ExamPaperResponse>> getPaper(@PathVariable Integer id) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.findPaperById(id)));
    }

    @PostMapping("/papers")
    public ResponseEntity<QbankResponse<ExamPaperResponse>> createPaper(@RequestBody @Valid ExamPaperRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.createPaper(req)));
    }

    @PutMapping("/papers/{id}")
    public ResponseEntity<QbankResponse<ExamPaperResponse>> updatePaper(@PathVariable Integer id,
                                                                          @RequestBody @Valid ExamPaperRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.updatePaper(id, req)));
    }

    @DeleteMapping("/papers/{id}")
    public ResponseEntity<QbankResponse<Void>> deletePaper(@PathVariable Integer id) {
        adminService.deletePaper(id);
        return ResponseEntity.ok(QbankResponse.ok(null));
    }

    // ── 문항 ──────────────────────────────────────────────────

    @GetMapping("/papers/{paperId}/questions")
    public ResponseEntity<QbankResponse<List<QuestionAdminResponse>>> getQuestions(@PathVariable Integer paperId) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.findQuestionsByPaper(paperId)));
    }

    @PostMapping("/papers/{paperId}/questions")
    public ResponseEntity<QbankResponse<QuestionAdminResponse>> createQuestion(@PathVariable Integer paperId,
                                                                                @RequestBody @Valid QuestionRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.createQuestion(paperId, req)));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QbankResponse<QuestionAdminResponse>> updateQuestion(@PathVariable Integer id,
                                                                                @RequestBody @Valid QuestionRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.updateQuestion(id, req)));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<QbankResponse<Void>> deleteQuestion(@PathVariable Integer id) {
        adminService.deleteQuestion(id);
        return ResponseEntity.ok(QbankResponse.ok(null));
    }

    // ── 보기 ──────────────────────────────────────────────────

    @PostMapping("/questions/{id}/choices")
    public ResponseEntity<QbankResponse<ChoiceAdminResponse>> createChoice(@PathVariable Integer id,
                                                                             @RequestBody @Valid QuestionChoiceRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.createChoice(id, req)));
    }

    @PutMapping("/choices/{id}")
    public ResponseEntity<QbankResponse<ChoiceAdminResponse>> updateChoice(@PathVariable Integer id,
                                                                             @RequestBody @Valid QuestionChoiceRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.updateChoice(id, req)));
    }

    @DeleteMapping("/choices/{id}")
    public ResponseEntity<QbankResponse<Void>> deleteChoice(@PathVariable Integer id) {
        adminService.deleteChoice(id);
        return ResponseEntity.ok(QbankResponse.ok(null));
    }

    // ── 세션 / 채점 ───────────────────────────────────────────

    @GetMapping("/sessions")
    public ResponseEntity<QbankResponse<List<SessionResponse>>> getSessions(
            @RequestParam(required = false) Integer paperId,
            @RequestParam(required = false) String examineeName) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.findAllSessions(paperId, examineeName)));
    }

    @GetMapping("/sessions/{id}/answers")
    public ResponseEntity<QbankResponse<List<AnswerDetailResponse>>> getAnswers(@PathVariable Integer id) {
        return ResponseEntity.ok(QbankResponse.ok(adminService.findAnswersBySession(id)));
    }

    @PostMapping("/sessions/{id}/grade")
    public ResponseEntity<QbankResponse<Void>> grade(@PathVariable Integer id,
                                                      @RequestBody GradeRequest req) {
        adminService.grade(id, req);
        return ResponseEntity.ok(QbankResponse.ok(null));
    }
}
