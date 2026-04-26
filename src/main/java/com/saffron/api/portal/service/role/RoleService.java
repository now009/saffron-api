package com.saffron.api.portal.service.role;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.role.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getRoles(String roleCode, String roleName);

    ApiResponse saveRole(RoleDto roleDto);

    ApiResponse updateRole(RoleDto roleDto);

    ApiResponse deleteRole(String roleCode);

    ApiResponse checkRoleCode(String roleCode);

    String getNextRoleCode();
}
