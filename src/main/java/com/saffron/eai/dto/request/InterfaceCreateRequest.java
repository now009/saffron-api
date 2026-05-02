package com.saffron.eai.dto.request;

import jakarta.validation.constraints.NotBlank;

public class InterfaceCreateRequest {

    @NotBlank
    private String interfaceId;

    @NotBlank
    private String name;

    private String sourceSystem;
    private String targetSystem;

    @NotBlank
    private String adapterType;

    private String direction;
    private Boolean isParallel;
    private Boolean isActive;
    private String description;
    private String tags;

    public InterfaceCreateRequest() {}

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
}
