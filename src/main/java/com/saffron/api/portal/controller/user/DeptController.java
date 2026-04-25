package com.saffron.api.portal.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/depts")
public class DeptController {

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(Map.of("data", java.util.List.of()));
    }

    @GetMapping("/{deptId}")
    public ResponseEntity<?> get(@PathVariable String deptId) {
        return ResponseEntity.ok(Map.of("deptId", deptId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/{deptId}")
    public ResponseEntity<?> update(@PathVariable String deptId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<?> delete(@PathVariable String deptId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
