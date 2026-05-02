package com.saffron.portal.dto.role;

import java.time.LocalDateTime;

public class RoleMappingDto {

    private String mappingId;
    private String roleId;
    private String userId;
    private String programId;
    private String canRead;
    private String canWrite;
    private String canUpdate;
    private String canDelete;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updateUser;
    private LocalDateTime updatedDate;

    public RoleMappingDto() {}

    public String getMappingId() { return mappingId; }
    public void setMappingId(String mappingId) { this.mappingId = mappingId; }

    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getProgramId() { return programId; }
    public void setProgramId(String programId) { this.programId = programId; }

    public String getCanRead() { return canRead; }
    public void setCanRead(String canRead) { this.canRead = canRead; }

    public String getCanWrite() { return canWrite; }
    public void setCanWrite(String canWrite) { this.canWrite = canWrite; }

    public String getCanUpdate() { return canUpdate; }
    public void setCanUpdate(String canUpdate) { this.canUpdate = canUpdate; }

    public String getCanDelete() { return canDelete; }
    public void setCanDelete(String canDelete) { this.canDelete = canDelete; }

    public String getCreatedUser() { return createdUser; }
    public void setCreatedUser(String createdUser) { this.createdUser = createdUser; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getUpdateUser() { return updateUser; }
    public void setUpdateUser(String updateUser) { this.updateUser = updateUser; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
