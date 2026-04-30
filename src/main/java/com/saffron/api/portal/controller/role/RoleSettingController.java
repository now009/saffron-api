package com.saffron.api.portal.controller.role;

import com.saffron.api.portal.dto.common.CommonResponse;
import com.saffron.api.portal.dto.role.RoleDeptDto;
import com.saffron.api.portal.dto.role.RoleDeptRequest;
import com.saffron.api.portal.dto.role.RoleDto;
import com.saffron.api.portal.dto.role.RoleMenuDto;
import com.saffron.api.portal.dto.role.RoleMenuRequest;
import com.saffron.api.portal.dto.role.RoleUserDto;
import com.saffron.api.portal.dto.role.RoleUserRequest;
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

    /**
     * 역할 목록 조회 (사용중인 역할만)
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<RoleDto>>> list() {
        return ResponseEntity.ok(CommonResponse.success(roleService.getActiveRoles()));
    }

    /**
     * 역할별 전체 메뉴 + 할당여부 조회
     */
    @GetMapping("/{roleCode}/menus")
    public ResponseEntity<CommonResponse<List<RoleMenuDto>>> getRoleMenus(
            @PathVariable String roleCode,
            @RequestParam(required = false) String site) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleMenus(roleCode, site)));
    }

    /**
     * 역할별 메뉴 권한 저장
     */
    @PostMapping("/{roleCode}/menus")
    public ResponseEntity<CommonResponse<List<RoleMenuDto>>> saveRoleMenus(
            @PathVariable String roleCode,
            @RequestParam(required = false) String site,
            @RequestBody List<RoleMenuRequest> requests) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        roleService.saveRoleMenus(roleCode, requests);
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleMenus(roleCode, site)));
    }

    /**
     * 역할에 할당된 사용자 조회 (직접 + 부서 경유)
     */
    @GetMapping("/{roleCode}/users")
    public ResponseEntity<CommonResponse<List<RoleUserDto>>> getRoleUsers(@PathVariable String roleCode) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleUsers(roleCode)));
    }

    /**
     * 역할에 사용자 할당 저장
     */
    @PostMapping("/{roleCode}/users")
    public ResponseEntity<CommonResponse<List<RoleUserDto>>> saveRoleUsers(
            @PathVariable String roleCode,
            @RequestBody List<RoleUserRequest> requests) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        roleService.saveRoleUsers(roleCode, requests);
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleUsers(roleCode)));
    }

    /**
     * 역할에 할당된 부서 조회
     */
    @GetMapping("/{roleCode}/depts")
    public ResponseEntity<CommonResponse<List<RoleDeptDto>>> getRoleDepts(@PathVariable String roleCode) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleDepts(roleCode)));
    }

    /**
     * 역할에 부서 할당 저장
     */
    @PostMapping("/{roleCode}/depts")
    public ResponseEntity<CommonResponse<List<RoleDeptDto>>> saveRoleDepts(
            @PathVariable String roleCode,
            @RequestBody List<RoleDeptRequest> requests) {
        if (!roleService.existsRole(roleCode)) {
            return ResponseEntity.status(404).body(CommonResponse.notFound("존재하지 않는 권한입니다"));
        }
        roleService.saveRoleDepts(roleCode, requests);
        return ResponseEntity.ok(CommonResponse.success(roleService.getRoleDepts(roleCode)));
    }
}
