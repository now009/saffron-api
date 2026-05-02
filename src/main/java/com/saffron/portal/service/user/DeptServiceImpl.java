package com.saffron.portal.service.user;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.user.DeptDto;
import com.saffron.portal.mapper.DeptMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;

    public DeptServiceImpl(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    @Override
    public List<DeptDto> getDepts(String deptId, String deptName, String deptCode) {
        return deptMapper.selectDeptList(deptId, deptName, deptCode);
    }

    @Override
    public ApiResponse saveDept(DeptDto deptDto) {
        if (deptMapper.countDept(deptDto.getDeptId()) > 0) {
            return ApiResponse.fail("이미 존재하는 부서ID입니다");
        }
        try {
            deptMapper.insertDept(deptDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateDept(DeptDto deptDto) {
        if (deptMapper.countDept(deptDto.getDeptId()) == 0) {
            return ApiResponse.fail("부서가 존재하지 않습니다");
        }
        try {
            deptMapper.updateDept(deptDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteDept(String deptId) {
        if (deptMapper.countDept(deptId) == 0) {
            return ApiResponse.fail("부서가 존재하지 않습니다");
        }
        if (deptMapper.countChildDepts(deptId) > 0) {
            return ApiResponse.fail("하위 부서가 존재합니다");
        }
        if (deptMapper.countUsersInDept(deptId) > 0) {
            return ApiResponse.fail("소속 사용자가 존재합니다");
        }
        deptMapper.deleteDept(deptId);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public ApiResponse checkDeptCode(String deptCode) {
        if (deptMapper.countDeptCode(deptCode) > 0) {
            return ApiResponse.fail("이미 사용중인 부서코드입니다");
        }
        return ApiResponse.success("사용 가능한 부서코드입니다");
    }

    @Override
    public String getNextDeptId() {
        return deptMapper.selectNextDeptId();
    }
}
