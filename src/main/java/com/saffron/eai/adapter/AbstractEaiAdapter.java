// com.saffron.eai.adapter.AbstractEaiAdapter.java

package com.saffron.eai.adapter;

import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.domain.EaiMessageHistory;
import com.saffron.eai.mapper.EaiMessageHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractEaiAdapter implements EaiAdapter {

    @Autowired
    private EaiMessageHistoryMapper historyRepository;

    @Autowired
    private AesEncryptor aesEncryptor;

    // ── 공통: 표준 헤더 생성 ──
    protected EaiMessage.Header buildHeader(EaiMessage msg) {
        return EaiMessage.Header.builder()
                .messageId(java.util.UUID.randomUUID().toString())
                .interfaceId(msg.getInterfaceId())
                .sendSystem(msg.getSourceSystem())
                .sendTimestamp(java.time.LocalDateTime.now())
                .build();
    }

    // ── 공통: AES-256 암호화 ──
    protected String encrypt(String data) {
        return aesEncryptor.encrypt(data);
    }

    protected String decrypt(String data) {
        return aesEncryptor.decrypt(data);
    }

    // ── 공통: 처리 이력 저장 ──
    protected void saveHistory(EaiMessage message, EaiResponse response, String status) {
        EaiMessageHistory history = EaiMessageHistory.builder()
                .interfaceId(message.getInterfaceId())
                .direction(message.getDirection())
                .sourceSystem(message.getSourceSystem())
                .targetSystem(message.getTargetSystem())
                .messageBody(message.getPayload())
                .responseBody(response != null ? response.getBody() : null)
                .status(status)
                .processingMs(response != null ? response.getProcessingMs() : 0)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }

    // ── 공통: 로깅 ──
    protected void logTransaction(EaiMessage msg, EaiResponse resp) {
        log.info("[EAI] interfaceId={} direction={} status={} processingMs={}",
                msg.getInterfaceId(), msg.getDirection(),
                resp.getStatus(), resp.getProcessingMs());
    }
}