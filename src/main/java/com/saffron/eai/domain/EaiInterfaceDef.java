package com.saffron.eai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EaiInterfaceDef {

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

    private List<EaiMappingRule> mappingRules;
    private List<EaiRoutingRule> routingRules;

    public boolean isActive() {
        return Boolean.TRUE.equals(isActive);
    }

    public boolean isParallel() {
        return Boolean.TRUE.equals(isParallel);
    }
}
