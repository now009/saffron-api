package com.saffron.portal.mapper;

import com.saffron.portal.dto.role.RoleDeptDto;
import com.saffron.portal.dto.role.RoleDto;
import com.saffron.portal.dto.role.RoleMenuDto;
import com.saffron.portal.dto.role.RoleUserDto;
import com.saffron.portal.dto.role.UserMenuPermDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<RoleDto> selectRoleList(@Param("roleCode") String roleCode,
                                 @Param("roleName") String roleName);

    List<RoleDto> selectActiveRoles();

    List<UserMenuPermDto> selectUserMenuPermissions(@Param("userId") String userId,
                                                    @Param("site") String site);

    List<RoleMenuDto> selectRoleMenus(@Param("roleCode") String roleCode,
                                      @Param("site") String site);

    void deleteRoleMenus(@Param("roleCode") String roleCode);

    void insertRoleMenus(@Param("roleCode") String roleCode,
                         @Param("menuIds") List<String> menuIds);

    List<RoleUserDto> selectRoleUsers(@Param("roleCode") String roleCode);

    void deleteRoleUsers(@Param("roleCode") String roleCode);

    void insertRoleUsers(@Param("roleCode") String roleCode,
                         @Param("userIds") List<String> userIds);

    List<RoleDeptDto> selectRoleDepts(@Param("roleCode") String roleCode);

    void deleteRoleDepts(@Param("roleCode") String roleCode);

    void insertRoleDepts(@Param("roleCode") String roleCode,
                         @Param("deptIds") List<String> deptIds);

    int insertRole(RoleDto roleDto);

    int updateRole(RoleDto roleDto);

    int countRole(@Param("roleCode") String roleCode);

    int deleteRole(@Param("roleCode") String roleCode);

    String selectNextRoleCode();
}
