package com.saffron.eai.kafka;

import com.saffron.eai.common.EaiMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class EaiMessageProducer implements EaiMessagePublisher {

    private final KafkaTemplate<String, EaiMessage> kafkaTemplate;

    public void publishRequest(EaiMessage message) {
        log.info("[Producer] 발행 interfaceId={}", message.getInterfaceId());
        kafkaTemplate.send("eai.interface.request", message.getInterfaceId(), message);
    }

    public void publishResponse(EaiMessage message) {
        kafkaTemplate.send("eai.interface.response", message.getInterfaceId(), message);
    }

    @Override
    public void publishDlq(EaiMessage message) {
        kafkaTemplate.send("eai.interface.dlq", message);
    }
}
