package com.saffron.api.portal.service.role;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.role.RoleDeptDto;
import com.saffron.api.portal.dto.role.RoleDto;
import com.saffron.api.portal.dto.role.RoleMenuDto;
import com.saffron.api.portal.dto.role.RoleMenuRequest;
import com.saffron.api.portal.dto.role.RoleUserDto;
import com.saffron.api.portal.dto.role.UserMenuPermDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getRoles(String roleCode, String roleName);

    List<RoleDto> getActiveRoles();

    boolean existsRole(String roleCode);

    List<UserMenuPermDto> getUserMenuPermissions(String userId);

    List<RoleMenuDto> getRoleMenus(String roleCode);

    void saveRoleMenus(String roleCode, List<RoleMenuRequest> requests);

    List<RoleUserDto> getRoleUsers(String roleCode);

    List<RoleDeptDto> getRoleDepts(String roleCode);

    ApiResponse saveRole(RoleDto roleDto);

    ApiResponse updateRole(RoleDto roleDto);

    ApiResponse deleteRole(String roleCode);

    ApiResponse checkRoleCode(String roleCode);

    String getNextRoleCode();
}
