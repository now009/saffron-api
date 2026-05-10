package com.saffron.api.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "false", matchIfMissing = true)
public class KafkaProducerServiceStub implements KafkaProducerService {

    @Override
    public void send(String topic, String message) {
        log.debug("Kafka disabled — skip send: topic={}", topic);
    }

    @Override
    public void send(String topic, String key, String message) {
        log.debug("Kafka disabled — skip send: topic={}, key={}", topic, key);
    }
}
