package com.saffron.api.portal.dto.code;

import java.time.LocalDateTime;

public class CodeDto {

    private String code;
    private String parentCode;
    private String codeName;
    private Integer sortOrder;
    private String useYn;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updateUser;
    private LocalDateTime updatedDate;

    public CodeDto() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getParentCode() { return parentCode; }
    public void setParentCode(String parentCode) { this.parentCode = parentCode; }

    public String getCodeName() { return codeName; }
    public void setCodeName(String codeName) { this.codeName = codeName; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getCreatedUser() { return createdUser; }
    public void setCreatedUser(String createdUser) { this.createdUser = createdUser; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getUpdateUser() { return updateUser; }
    public void setUpdateUser(String updateUser) { this.updateUser = updateUser; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
