package com.saffron.api.portal.service.role;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.role.RoleDto;
import com.saffron.api.portal.dto.role.UserMenuPermDto;
import com.saffron.api.portal.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<UserMenuPermDto> getUserMenuPermissions(String userId) {
        return roleMapper.selectUserMenuPermissions(userId);
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
        if (roleMapper.countRoleInMapping(roleCode) > 0) {
            return ApiResponse.fail("사용중인 권한입니다");
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
