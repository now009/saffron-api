package com.saffron.eai.dto;

import java.time.LocalDateTime;

public class EaiRoutingRuleDto {

    private Long id;
    private String interfaceId;
    private String conditionExpr;
    private Long targetAdapterId;
    private String targetSystem;
    private Boolean isParallel;
    private Integer sortOrder;
    private LocalDateTime createdAt;

    public EaiRoutingRuleDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInterfaceId() { return interfaceId; }
    public void setInterfaceId(String interfaceId) { this.interfaceId = interfaceId; }

    public String getConditionExpr() { return conditionExpr; }
    public void setConditionExpr(String conditionExpr) { this.conditionExpr = conditionExpr; }

    public Long getTargetAdapterId() { return targetAdapterId; }
    public void setTargetAdapterId(Long targetAdapterId) { this.targetAdapterId = targetAdapterId; }

    public String getTargetSystem() { return targetSystem; }
    public void setTargetSystem(String targetSystem) { this.targetSystem = targetSystem; }

    public Boolean getIsParallel() { return isParallel; }
    public void setIsParallel(Boolean isParallel) { this.isParallel = isParallel; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
