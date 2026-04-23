package com.saffron.api.controller.role;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/roles")
public class RoleController {

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(Map.of(
                "page", page,
                "size", size,
                "data", java.util.List.of()
        ));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<?> get(@PathVariable String roleId) {
        return ResponseEntity.ok(Map.of("roleId", roleId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<?> update(@PathVariable String roleId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> delete(@PathVariable String roleId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
