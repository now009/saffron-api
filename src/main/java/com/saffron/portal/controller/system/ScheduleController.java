package com.saffron.portal.controller.system;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/schedules")
public class ScheduleController {

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(Map.of(
                "page", page,
                "size", size,
                "data", java.util.List.of()
        ));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> get(@PathVariable String scheduleId) {
        return ResponseEntity.ok(Map.of("scheduleId", scheduleId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<?> update(@PathVariable String scheduleId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<?> delete(@PathVariable String scheduleId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PostMapping("/{scheduleId}/run")
    public ResponseEntity<?> run(@PathVariable String scheduleId) {
        return ResponseEntity.ok(Map.of("result", "ok", "scheduleId", scheduleId));
    }
}
