package com.saffron.eai.dto;

public class EaiCodeMapDto {

    private Long id;
    private String groupId;
    private String sourceCode;
    private String targetCode;
    private String description;
    private Integer sortOrder;

    public EaiCodeMapDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    public String getTargetCode() { return targetCode; }
    public void setTargetCode(String targetCode) { this.targetCode = targetCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
