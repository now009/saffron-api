package com.saffron.qbank.controller;

import com.saffron.qbank.common.QbankResponse;
import com.saffron.qbank.dto.request.SessionStartRequest;
import com.saffron.qbank.dto.request.SubmitRequest;
import com.saffron.qbank.dto.response.*;
import com.saffron.qbank.service.QbankExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qbank/exam")
@RequiredArgsConstructor
public class QbankExamController {

    private final QbankExamService examService;

    @GetMapping("/types")
    public ResponseEntity<QbankResponse<List<ExamTypeResponse>>> getTypes() {
        return ResponseEntity.ok(QbankResponse.ok(examService.findActiveTypes()));
    }

    @GetMapping("/subjects")
    public ResponseEntity<QbankResponse<List<ExamSubjectResponse>>> getSubjects() {
        return ResponseEntity.ok(QbankResponse.ok(examService.findActiveSubjects()));
    }

    @GetMapping("/papers")
    public ResponseEntity<QbankResponse<List<ExamPaperResponse>>> getPapers(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer subjectId) {
        return ResponseEntity.ok(QbankResponse.ok(examService.findActivePapers(typeId, subjectId)));
    }

    @PostMapping("/sessions")
    public ResponseEntity<QbankResponse<SessionStartResponse>> startSession(@RequestBody @Valid SessionStartRequest req) {
        return ResponseEntity.ok(QbankResponse.ok(examService.startSession(req)));
    }

    @PostMapping("/sessions/{id}/submit")
    public ResponseEntity<QbankResponse<Void>> submitAnswers(@PathVariable Integer id,
                                                              @RequestBody @Valid SubmitRequest req) {
        examService.submitAnswers(id, req);
        return ResponseEntity.ok(QbankResponse.ok(null));
    }
}
