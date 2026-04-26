package com.saffron.api.portal.controller.role;

import com.saffron.api.portal.dto.common.CommonResponse;
import com.saffron.api.portal.dto.role.RoleDeptDto;
import com.saffron.api.portal.dto.role.RoleDto;
import com.saffron.api.portal.dto.role.RoleMenuDto;
import com.saffron.api.portal.dto.role.RoleMenuRequest;
import com.saffron.api.portal.dto.role.RoleUserDto;
import com.saffron.api.portal.service.role.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portal/rolesetting")
public class RoleSettingController {

    private final RoleService roleService;

    public RoleSettingController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoleDto>>> list() {
        return ResponseEntity.ok(CommonResponse.success(roleService.getActiveRoles()));
    }

    @GetMapping("/{roleCode}/menus")
    public ResponseEntity<CommonResponse<List<RoleMenuDto>>> getRoleMenus(@PathVariable String roleCode) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleMenus(roleCode)));
    }

    @PostMapping("/{roleCode}/menus")
    public ResponseEntity<CommonResponse<List<RoleMenuDto>>> saveRoleMenus(
            @PathVariable String roleCode,
            @RequestBody List<RoleMenuRequest> requests) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        roleService.saveRoleMenus(roleCode, requests);
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleMenus(roleCode)));
    }

    @GetMapping("/{roleCode}/users")
    public ResponseEntity<CommonResponse<List<RoleUserDto>>> getRoleUsers(@PathVariable String roleCode) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleUsers(roleCode)));
    }

    @GetMapping("/{roleCode}/depts")
    public ResponseEntity<CommonResponse<List<RoleDeptDto>>> getRoleDepts(@PathVariable String roleCode) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleDepts(roleCode)));
    }
}
