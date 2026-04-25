package com.saffron.api.portal.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/users")
public class UserController {

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(Map.of(
                "page", page,
                "size", size,
                "data", java.util.List.of()
        ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable String userId) {
        return ResponseEntity.ok(Map.of("userId", userId));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> update(@PathVariable String userId,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable String userId) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
