package com.saffron.portal.dto.user;

import java.time.LocalDateTime;

public class DeptDto {

    private String deptId;
    private String parentDeptId;
    private String deptCode;
    private String deptName;
    private Integer deptLevel;
    private Integer sortOrder;
    private String useYn;
    private String treePath;
    private String deptNameTree;
    private Integer depth;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updateUser;
    private LocalDateTime updatedDate;

    public DeptDto() {}

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public String getParentDeptId() { return parentDeptId; }
    public void setParentDeptId(String parentDeptId) { this.parentDeptId = parentDeptId; }

    public String getDeptCode() { return deptCode; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public Integer getDeptLevel() { return deptLevel; }
    public void setDeptLevel(Integer deptLevel) { this.deptLevel = deptLevel; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getTreePath() { return treePath; }
    public void setTreePath(String treePath) { this.treePath = treePath; }

    public String getDeptNameTree() { return deptNameTree; }
    public void setDeptNameTree(String deptNameTree) { this.deptNameTree = deptNameTree; }

    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }

    public String getCreatedUser() { return createdUser; }
    public void setCreatedUser(String createdUser) { this.createdUser = createdUser; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getUpdateUser() { return updateUser; }
    public void setUpdateUser(String updateUser) { this.updateUser = updateUser; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
