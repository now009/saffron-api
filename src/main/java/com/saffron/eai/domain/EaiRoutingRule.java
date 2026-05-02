package com.saffron.eai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EaiRoutingRule {

    private Long id;
    private String interfaceId;
    private String conditionExpr;
    private Long targetAdapterId;
    private String targetSystem;
    private Boolean isParallel;
    private Integer sortOrder;
    private LocalDateTime createdAt;
}
