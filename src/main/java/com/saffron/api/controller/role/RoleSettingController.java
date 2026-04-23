package com.saffron.api.controller.role;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portal/role-settings")
public class RoleSettingController {

    @GetMapping("/{roleId}/menus")
    public ResponseEntity<?> getMenusByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(Map.of("roleId", roleId, "menus", java.util.List.of()));
    }

    @PostMapping("/{roleId}/menus")
    public ResponseEntity<?> saveMenusByRole(@PathVariable String roleId,
                                             @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @GetMapping("/{roleId}/users")
    public ResponseEntity<?> getUsersByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(Map.of("roleId", roleId, "users", java.util.List.of()));
    }

    @PostMapping("/{roleId}/users")
    public ResponseEntity<?> saveUsersByRole(@PathVariable String roleId,
                                             @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
