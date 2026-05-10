package com.saffron.eai.kafka;

import com.saffron.eai.common.EaiMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "false", matchIfMissing = true)
public class EaiMessagePublisherStub implements EaiMessagePublisher {

    @Override
    public void publishRequest(EaiMessage message) {
        log.debug("Kafka disabled — skip publishRequest: interfaceId={}", message.getInterfaceId());
    }

    @Override
    public void publishResponse(EaiMessage message) {
        log.debug("Kafka disabled — skip publishResponse: interfaceId={}", message.getInterfaceId());
    }

    @Override
    public void publishDlq(EaiMessage message) {
        log.debug("Kafka disabled — skip publishDlq: interfaceId={}", message.getInterfaceId());
    }
}
