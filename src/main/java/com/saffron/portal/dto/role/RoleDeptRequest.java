package com.saffron.portal.dto.role;

/**
 * 역할에 부서를 할당/해제하기 위한 요청 DTO.
 * useYn = 'Y' 인 부서만 dept_role 테이블에 저장된다.
 */
public class RoleDeptRequest {

    private String deptId;
    private String useYn;

    public RoleDeptRequest() {}

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
}
