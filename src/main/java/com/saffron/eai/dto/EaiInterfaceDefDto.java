package com.saffron.eai.dto;

import java.time.LocalDateTime;

public class EaiInterfaceDefDto {

    private Long id;
    private String interfaceId;
    private String name;
    private String sourceSystem;
    private String targetSystem;
    private String adapterType;
    private String direction;
    private Boolean isParallel;
    private Boolean isActive;
    private String description;
    private String tags;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EaiInterfaceDefDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInterfaceId() { return interfaceId; }
    public void setInterfaceId(String interfaceId) { this.interfaceId = interfaceId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getTargetSystem() { return targetSystem; }
    public void setTargetSystem(String targetSystem) { this.targetSystem = targetSystem; }

    public String getAdapterType() { return adapterType; }
    public void setAdapterType(String adapterType) { this.adapterType = adapterType; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public Boolean getIsParallel() { return isParallel; }
    public void setIsParallel(Boolean isParallel) { this.isParallel = isParallel; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
