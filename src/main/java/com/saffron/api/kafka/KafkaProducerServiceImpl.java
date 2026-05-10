package com.saffron.api.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka send failed: topic={}, error={}", topic, ex.getMessage());
                    } else {
                        log.debug("Kafka send ok: topic={}, offset={}",
                                topic, result.getRecordMetadata().offset());
                    }
                });
    }

    @Override
    public void send(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka send failed: topic={}, key={}, error={}", topic, key, ex.getMessage());
                    } else {
                        log.debug("Kafka send ok: topic={}, key={}, offset={}",
                                topic, key, result.getRecordMetadata().offset());
                    }
                });
    }
}
