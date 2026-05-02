package com.saffron.eai.controller;

import com.saffron.eai.dto.response.MessageHistoryResponse;
import com.saffron.eai.service.EaiMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eai/messages")
@RequiredArgsConstructor
public class EaiMessageController {

    private final EaiMessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageHistoryResponse>> list(
            @RequestParam(required = false) String interfaceId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(messageService.findHistory(interfaceId, status, keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageHistoryResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.findHistoryById(id));
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<Void> retry(@PathVariable Long id) {
        messageService.retry(id);
        return ResponseEntity.ok().build();
    }
}
