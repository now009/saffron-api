package com.saffron.portal.dto.role;

public class RoleUserDto {

    private String userId;
    private String userName;
    private String deptName;
    private String position;
    private String assignType;

    public RoleUserDto() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getAssignType() { return assignType; }
    public void setAssignType(String assignType) { this.assignType = assignType; }
}
