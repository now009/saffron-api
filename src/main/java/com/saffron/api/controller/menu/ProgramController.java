package com.saffron.api.controller.menu;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/programs")
public class ProgramController {

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(Map.of(
                "page", page,
                "size", size,
                "data", java.util.List.of()
        ));
    }

    @GetMapping("/{programId}")
    public ResponseEntity<?> get(@PathVariable String programId) {
        return ResponseEntity.ok(Map.of("programId", programId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/{programId}")
    public ResponseEntity<?> update(@PathVariable String programId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<?> delete(@PathVariable String programId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
