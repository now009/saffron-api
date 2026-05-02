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
public class EaiMessageHistory {

    private Long id;
    private String messageId;
    private String interfaceId;
    private String direction;
    private String sourceSystem;
    private String targetSystem;
    private String status;
    private String messageBody;
    private String responseBody;
    private String errorMessage;
    private Integer retryCount;
    private Long processingMs;
    private Integer kafkaPartition;
    private Long kafkaOffset;
    private Boolean isDlq;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
