package com.saffron.qbank.controller;

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
    public ResponseEntity<List<ExamTypeResponse>> getTypes() {
        return ResponseEntity.ok(examService.findActiveTypes());
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<ExamSubjectResponse>> getSubjects() {
        return ResponseEntity.ok(examService.findActiveSubjects());
    }

    @GetMapping("/papers")
    public ResponseEntity<List<ExamPaperResponse>> getPapers(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer subjectId) {
        return ResponseEntity.ok(examService.findActivePapers(typeId, subjectId));
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionStartResponse> startSession(@RequestBody @Valid SessionStartRequest req) {
        return ResponseEntity.ok(examService.startSession(req));
    }

    @PostMapping("/sessions/{id}/submit")
    public ResponseEntity<Void> submitAnswers(@PathVariable Integer id,
                                               @RequestBody @Valid SubmitRequest req) {
        examService.submitAnswers(id, req);
        return ResponseEntity.ok().build();
    }
}
