package com.saffron.eai.dto.request;

import jakarta.validation.constraints.NotBlank;

public class MappingRuleRequest {

    @NotBlank
    private String interfaceId;

    @NotBlank
    private String sourcePath;

    @NotBlank
    private String targetPath;

    private String transformType;
    private String transformExpr;
    private String defaultValue;
    private Boolean isRequired;
    private Integer sortOrder;

    public MappingRuleRequest() {}

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
}
