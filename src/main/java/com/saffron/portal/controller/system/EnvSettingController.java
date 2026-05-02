package com.saffron.portal.controller.system;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/env-settings")
public class EnvSettingController {

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(Map.of("data", java.util.List.of()));
    }

    @GetMapping("/{settingKey}")
    public ResponseEntity<?> get(@PathVariable String settingKey) {
        return ResponseEntity.ok(Map.of("settingKey", settingKey));
    }

    @PutMapping("/{settingKey}")
    public ResponseEntity<?> update(@PathVariable String settingKey,
                                    @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @PutMapping("/bulk")
    public ResponseEntity<?> bulkUpdate(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
