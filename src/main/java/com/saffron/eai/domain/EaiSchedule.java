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
public class EaiSchedule {

    private Long id;
    private String interfaceId;
    private String scheduleName;
    private String cronExpr;
    private Boolean isActive;
    private LocalDateTime lastRunAt;
    private String lastRunStatus;
    private LocalDateTime nextRunAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isActive() {
        return Boolean.TRUE.equals(isActive);
    }
}
