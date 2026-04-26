package com.saffron.api.portal.dto.code;

import java.util.List;

public class CodeTreeDto {

    private String code;
    private String parentCode;
    private String codeName;
    private Integer sortOrder;
    private String useYn;
    private List<CodeTreeDto> children;

    public CodeTreeDto() {}

    public CodeTreeDto(CodeDto dto) {
        this.code       = dto.getCode();
        this.parentCode = dto.getParentCode();
        this.codeName   = dto.getCodeName();
        this.sortOrder  = dto.getSortOrder();
        this.useYn      = dto.getUseYn();
    }

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

    public List<CodeTreeDto> getChildren() { return children; }
    public void setChildren(List<CodeTreeDto> children) { this.children = children; }
}
