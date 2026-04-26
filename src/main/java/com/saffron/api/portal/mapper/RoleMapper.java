package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.role.RoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<RoleDto> selectRoleList(@Param("roleCode") String roleCode,
                                 @Param("roleName") String roleName);

    int insertRole(RoleDto roleDto);

    int updateRole(RoleDto roleDto);

    int countRole(@Param("roleCode") String roleCode);

    int countRoleInMapping(@Param("roleCode") String roleCode);

    int deleteRole(@Param("roleCode") String roleCode);

    String selectNextRoleCode();
}
