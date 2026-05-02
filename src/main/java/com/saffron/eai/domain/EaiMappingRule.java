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
public class EaiMappingRule {

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
}
