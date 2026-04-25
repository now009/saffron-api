package com.saffron.api.portal.controller.system;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/codes")
public class CodeController {

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String groupCode,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(Map.of(
                "page", page,
                "size", size,
                "data", java.util.List.of()
        ));
    }

    @GetMapping("/groups")
    public ResponseEntity<?> groups() {
        return ResponseEntity.ok(Map.of("data", java.util.List.of()));
    }

    @GetMapping("/{codeId}")
    public ResponseEntity<?> get(@PathVariable String codeId) {
        return ResponseEntity.ok(Map.of("codeId", codeId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/{codeId}")
    public ResponseEntity<?> update(@PathVariable String codeId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @DeleteMapping("/{codeId}")
    public ResponseEntity<?> delete(@PathVariable String codeId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
