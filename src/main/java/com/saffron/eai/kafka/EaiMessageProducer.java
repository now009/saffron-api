package com.saffron.eai.kafka;

import com.saffron.eai.common.EaiMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EaiMessageProducer {

    private final KafkaTemplate<String, EaiMessage> kafkaTemplate;

    public void publishRequest(EaiMessage message) {
        log.info("[Producer] 발행 interfaceId={}", message.getInterfaceId());
        kafkaTemplate.send("eai.interface.request", message.getInterfaceId(), message);
    }

    public void publishResponse(EaiMessage message) {
        kafkaTemplate.send("eai.interface.response", message.getInterfaceId(), message);
    }
}
