package com.saffron.portal.controller.role;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.role.RoleDto;
import com.saffron.portal.dto.role.UserMenuPermDto;
import com.saffron.portal.service.role.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<RoleDto>> list(@RequestBody(required = false) Map<String, String> params) {
        String roleCode = params != null ? params.get("roleCode") : null;
        String roleName = params != null ? params.get("roleName") : null;
        return ResponseEntity.ok(roleService.getRoles(roleCode, roleName));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody RoleDto roleDto) {
        ApiResponse result = roleService.saveRole(roleDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(roleService.getRoles(null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody RoleDto roleDto) {
        ApiResponse result = roleService.updateRole(roleDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(roleService.getRoles(null, null));
    }

    @PostMapping("/delete/{roleCode}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String roleCode) {
        return ResponseEntity.ok(roleService.deleteRole(roleCode));
    }

    @GetMapping("/check-code/{roleCode}")
    public ResponseEntity<ApiResponse> checkCode(@PathVariable String roleCode) {
        return ResponseEntity.ok(roleService.checkRoleCode(roleCode));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("roleCode", roleService.getNextRoleCode()));
    }

    @GetMapping("/user-menus/{userId}")
    public ResponseEntity<List<UserMenuPermDto>> userMenus(
            @PathVariable String userId,
            @RequestParam(required = false) String site) {
        return ResponseEntity.ok(roleService.getUserMenuPermissions(userId, site));
    }
}
