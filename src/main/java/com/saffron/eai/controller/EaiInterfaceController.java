package com.saffron.eai.controller;

import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.dto.request.InterfaceCreateRequest;
import com.saffron.eai.dto.response.InterfaceResponse;
import com.saffron.eai.kafka.EaiMessageProducer;
import com.saffron.eai.service.EaiInterfaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/eai/interfaces")
@RequiredArgsConstructor
public class EaiInterfaceController {

    private final EaiInterfaceService interfaceService;
    private final EaiMessageProducer producer;

    @GetMapping
    public ResponseEntity<List<InterfaceResponse>> list(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String adapterType,
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(interfaceService.findAll(status, adapterType, keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterfaceResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(interfaceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InterfaceResponse> create(@RequestBody @Valid InterfaceCreateRequest request) {
        return ResponseEntity.ok(interfaceService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterfaceResponse> update(@PathVariable Long id,
                                                     @RequestBody @Valid InterfaceCreateRequest request) {
        return ResponseEntity.ok(interfaceService.update(id, request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggle(@PathVariable Long id,
                                        @RequestBody Map<String, Boolean> body) {
        interfaceService.toggle(id, body.get("isActive"));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        interfaceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/receive/{interfaceId}")
    public ResponseEntity<Void> receive(@PathVariable String interfaceId,
                                         @RequestBody String payload) {
        EaiMessage message = EaiMessage.builder()
                .messageId(UUID.randomUUID().toString())
                .interfaceId(interfaceId)
                .payload(payload)
                .direction("RECEIVE")
                .build();
        producer.publishRequest(message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{interfaceId}/test")
    public ResponseEntity<EaiResponse> test(@PathVariable String interfaceId,
                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(interfaceService.testSend(interfaceId, body.get("payload")));
    }
}
