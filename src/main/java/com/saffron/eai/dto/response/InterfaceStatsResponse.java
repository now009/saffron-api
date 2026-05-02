package com.saffron.eai.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InterfaceStatsResponse {

    private String interfaceId;
    private String period;
    private long totalCount;
    private long successCount;
    private long failCount;
    private long dlqCount;
    private double successRate;
    private long avgProcessingMs;
    private long maxProcessingMs;
}
