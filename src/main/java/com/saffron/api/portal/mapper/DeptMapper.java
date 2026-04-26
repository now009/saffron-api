package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.user.DeptDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptMapper {

    List<DeptDto> selectDeptList(@Param("deptId") String deptId,
                                 @Param("deptName") String deptName,
                                 @Param("deptCode") String deptCode);

    int insertDept(DeptDto deptDto);

    int updateDept(DeptDto deptDto);

    int countDept(@Param("deptId") String deptId);

    int countChildDepts(@Param("deptId") String deptId);

    int countUsersInDept(@Param("deptId") String deptId);

    int deleteDept(@Param("deptId") String deptId);

    int countDeptCode(@Param("deptCode") String deptCode);

    String selectNextDeptId();
}
