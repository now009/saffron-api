package com.saffron.eai.dto;

import java.time.LocalDateTime;

public class EaiMappingRuleDto {

    private Long id;
    private String interfaceId;
    private String sourcePath;
    private String targetPath;
    private String transformType;
    private String transformExpr;
    private String defaultValue;
    private Boolean isRequired;
    private Integer sortOrder;
    private LocalDateTime createdAt;

    public EaiMappingRuleDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInterfaceId() { return interfaceId; }
    public void setInterfaceId(String interfaceId) { this.interfaceId = interfaceId; }

    public String getSourcePath() { return sourcePath; }
    public void setSourcePath(String sourcePath) { this.sourcePath = sourcePath; }

    public String getTargetPath() { return targetPath; }
    public void setTargetPath(String targetPath) { this.targetPath = targetPath; }

    public String getTransformType() { return transformType; }
    public void setTransformType(String transformType) { this.transformType = transformType; }

    public String getTransformExpr() { return transformExpr; }
    public void setTransformExpr(String transformExpr) { this.transformExpr = transformExpr; }

    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }

    public Boolean getIsRequired() { return isRequired; }
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
