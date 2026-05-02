package com.saffron.eai.adapter;

import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.domain.EaiMessageHistory;
import com.saffron.eai.repository.EaiMessageHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public abstract class AbstractEaiAdapter implements EaiAdapter {

    @Autowired
    protected EaiMessageHistoryRepository historyRepository;

    protected EaiMessage.Header buildHeader(EaiMessage msg) {
        return EaiMessage.Header.builder()
                .messageId(UUID.randomUUID().toString())
                .interfaceId(msg.getInterfaceId())
                .sendSystem(msg.getSourceSystem())
                .sendTimestamp(LocalDateTime.now())
                .build();
    }

    protected void saveHistory(EaiMessage message, EaiResponse response, String status) {
        EaiMessageHistory history = EaiMessageHistory.builder()
                .interfaceId(message.getInterfaceId())
                .direction(message.getDirection())
                .sourceSystem(message.getSourceSystem())
                .targetSystem(message.getTargetSystem())
                .messageBody(message.getPayload())
                .responseBody(response != null ? response.getBody() : null)
                .status(status)
                .processingMs(response != null ? response.getProcessingMs() : 0L)
                .createdAt(LocalDateTime.now())
                .build();
        historyRepository.insertHistory(history);
    }

    protected void logTransaction(EaiMessage msg, EaiResponse resp) {
        log.info("[EAI] interfaceId={} direction={} status={} processingMs={}",
                msg.getInterfaceId(), msg.getDirection(),
                resp.getStatus(), resp.getProcessingMs());
    }
}
