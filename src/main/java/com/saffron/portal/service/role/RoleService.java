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

import java.util.List;

public interface RoleService {

    List<RoleDto> getRoles(String roleCode, String roleName);

    List<RoleDto> getActiveRoles();

    boolean existsRole(String roleCode);

    List<UserMenuPermDto> getUserMenuPermissions(String userId, String site);

    List<RoleMenuDto> getRoleMenus(String roleCode, String site);

    void saveRoleMenus(String roleCode, List<RoleMenuRequest> requests);

    List<RoleUserDto> getRoleUsers(String roleCode);

    void saveRoleUsers(String roleCode, List<RoleUserRequest> requests);

    List<RoleDeptDto> getRoleDepts(String roleCode);

    void saveRoleDepts(String roleCode, List<RoleDeptRequest> requests);

    ApiResponse saveRole(RoleDto roleDto);

    ApiResponse updateRole(RoleDto roleDto);

    ApiResponse deleteRole(String roleCode);

    ApiResponse checkRoleCode(String roleCode);

    String getNextRoleCode();
}
