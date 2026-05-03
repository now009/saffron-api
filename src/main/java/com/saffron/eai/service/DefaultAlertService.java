package com.saffron.eai.service;

import com.saffron.eai.common.EaiMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DefaultAlertService implements AlertService {

    @Override
    public void sendValidationAlert(EaiMessage message, List<String> errors) {
        log.warn("[ALERT] 유효성 검사 실패 interfaceId={} errors={}", message.getInterfaceId(), errors);
    }

    @Override
    public void sendDlqAlert(EaiMessage message) {
        log.warn("[ALERT] DLQ 수신 interfaceId={} messageId={}", message.getInterfaceId(), message.getMessageId());
    }

    @Override
    public void sendRetryExhaustedAlert(EaiMessage message, Exception cause) {
        log.error("[ALERT] 최대 재시도 초과 interfaceId={} cause={}", message.getInterfaceId(), cause.getMessage());
    }
}
