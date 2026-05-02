package com.saffron.eai.dto.response;

import java.time.LocalDateTime;

public class MessageHistoryResponse {

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
    private Boolean isDlq;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public MessageHistoryResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getInterfaceId() { return interfaceId; }
    public void setInterfaceId(String interfaceId) { this.interfaceId = interfaceId; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getTargetSystem() { return targetSystem; }
    public void setTargetSystem(String targetSystem) { this.targetSystem = targetSystem; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessageBody() { return messageBody; }
    public void setMessageBody(String messageBody) { this.messageBody = messageBody; }

    public String getResponseBody() { return responseBody; }
    public void setResponseBody(String responseBody) { this.responseBody = responseBody; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }

    public Long getProcessingMs() { return processingMs; }
    public void setProcessingMs(Long processingMs) { this.processingMs = processingMs; }

    public Boolean getIsDlq() { return isDlq; }
    public void setIsDlq(Boolean isDlq) { this.isDlq = isDlq; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
