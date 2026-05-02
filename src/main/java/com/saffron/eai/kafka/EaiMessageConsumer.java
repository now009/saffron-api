package com.saffron.eai.kafka;

import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.NonRetryableException;
import com.saffron.eai.mapper.EaiMessageHistoryMapper;
import com.saffron.eai.service.AlertService;
import com.saffron.eai.service.WorkflowEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EaiMessageConsumer {

    private final WorkflowEngine workflowEngine;
    private final KafkaTemplate<String, EaiMessage> kafkaTemplate;
    private final AlertService alertService;
    private final EaiMessageHistoryMapper historyRepo;

    @KafkaListener(
            topics = "eai.interface.request",
            groupId = "eai-workflow-engine",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(@Payload EaiMessage message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                        @Header(KafkaHeaders.OFFSET) long offset,
                        Acknowledgment ack) {
        log.info("[Consumer] 수신 interfaceId={} partition={} offset={}",
                message.getInterfaceId(), partition, offset);
        try {
            workflowEngine.execute(message);
            ack.acknowledge();
        } catch (NonRetryableException e) {
            kafkaTemplate.send("eai.interface.dlq", message);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("[Consumer] 처리 실패, 재시도 예정: {}", e.getMessage());
            // 커밋 안 함 → Kafka 자동 재시도
        }
    }

    @KafkaListener(topics = "eai.interface.dlq", groupId = "eai-dlq-processor")
    public void consumeDlq(@Payload EaiMessage message, Acknowledgment ack) {
        log.warn("[DLQ] 수신 interfaceId={}", message.getInterfaceId());
        alertService.sendDlqAlert(message);
        historyRepo.markAsDlq(message.getMessageId());
        ack.acknowledge();
    }
}
