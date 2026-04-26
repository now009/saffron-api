package com.saffron.api.portal.service.user;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.user.DeptDto;

import java.util.List;

public interface DeptService {

    List<DeptDto> getDepts(String deptId, String deptName, String deptCode);

    ApiResponse saveDept(DeptDto deptDto);

    ApiResponse updateDept(DeptDto deptDto);

    ApiResponse deleteDept(String deptId);

    String getNextDeptId();
}
