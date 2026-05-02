package com.saffron.portal.service.role;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.role.RoleDeptDto;
import com.saffron.portal.dto.role.RoleDeptRequest;
import com.saffron.portal.dto.role.RoleDto;
import com.saffron.portal.dto.role.RoleMenuDto;
import com.saffron.portal.dto.role.RoleMenuRequest;
import com.saffron.portal.dto.role.RoleUserDto;
import com.saffron.portal.dto.role.RoleUserRequest;
import com.saffron.portal.dto.role.UserMenuPermDto;
import com.saffron.portal.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDto> getRoles(String roleCode, String roleName) {
        return roleMapper.selectRoleList(roleCode, roleName);
    }

    @Override
    public List<RoleDto> getActiveRoles() {
        return roleMapper.selectActiveRoles();
    }

    @Override
    public boolean existsRole(String roleCode) {
        return roleMapper.countRole(roleCode) > 0;
    }

    @Override
    public List<UserMenuPermDto> getUserMenuPermissions(String userId, String site) {
        return roleMapper.selectUserMenuPermissions(userId, site);
    }

    @Override
    public List<RoleMenuDto> getRoleMenus(String roleCode, String site) {
        return roleMapper.selectRoleMenus(roleCode, site);
    }

    @Override
    @Transactional
    public void saveRoleMenus(String roleCode, List<RoleMenuRequest> requests) {
        roleMapper.deleteRoleMenus(roleCode);
        List<String> menuIds = requests.stream()
                .filter(r -> "Y".equals(r.getUseYn()))
                .map(RoleMenuRequest::getMenuId)
                .collect(Collectors.toList());
        if (!menuIds.isEmpty()) {
            roleMapper.insertRoleMenus(roleCode, menuIds);
        }
    }

    @Override
    public List<RoleUserDto> getRoleUsers(String roleCode) {
        return roleMapper.selectRoleUsers(roleCode);
    }

    @Override
    @Transactional
    public void saveRoleUsers(String roleCode, List<RoleUserRequest> requests) {
        roleMapper.deleteRoleUsers(roleCode);
        List<String> userIds = requests.stream()
                .filter(r -> "Y".equals(r.getUseYn()))
                .map(RoleUserRequest::getUserId)
                .collect(Collectors.toList());
        if (!userIds.isEmpty()) {
            roleMapper.insertRoleUsers(roleCode, userIds);
        }
    }

    @Override
    public List<RoleDeptDto> getRoleDepts(String roleCode) {
        return roleMapper.selectRoleDepts(roleCode);
    }

    @Override
    @Transactional
    public void saveRoleDepts(String roleCode, List<RoleDeptRequest> requests) {
        roleMapper.deleteRoleDepts(roleCode);
        List<String> deptIds = requests.stream()
                .filter(r -> "Y".equals(r.getUseYn()))
                .map(RoleDeptRequest::getDeptId)
                .collect(Collectors.toList());
        if (!deptIds.isEmpty()) {
            roleMapper.insertRoleDepts(roleCode, deptIds);
        }
    }

    @Override
    public ApiResponse saveRole(RoleDto roleDto) {
        if (roleMapper.countRole(roleDto.getRoleCode()) > 0) {
            return ApiResponse.fail("이미 존재하는 권한코드입니다");
        }
        try {
            roleMapper.insertRole(roleDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateRole(RoleDto roleDto) {
        if (roleMapper.countRole(roleDto.getRoleCode()) == 0) {
            return ApiResponse.fail("권한이 존재하지 않습니다");
        }
        try {
            roleMapper.updateRole(roleDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteRole(String roleCode) {
        if (roleMapper.countRole(roleCode) == 0) {
            return ApiResponse.fail("권한이 존재하지 않습니다");
        }
        roleMapper.deleteRole(roleCode);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public ApiResponse checkRoleCode(String roleCode) {
        if (roleMapper.countRole(roleCode) > 0) {
            return ApiResponse.fail("이미 사용중인 권한코드입니다");
        }
        return ApiResponse.success("사용 가능한 권한코드입니다");
    }

    @Override
    public String getNextRoleCode() {
        return roleMapper.selectNextRoleCode();
    }
}
