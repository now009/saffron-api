package com.saffron.portal.dto.role;

public class RoleDeptDto {

    private String deptId;
    private String deptName;
    private Integer deptLevel;

    public RoleDeptDto() {}

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public Integer getDeptLevel() { return deptLevel; }
    public void setDeptLevel(Integer deptLevel) { this.deptLevel = deptLevel; }
}
