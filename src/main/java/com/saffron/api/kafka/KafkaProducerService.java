package com.saffron.api.kafka;

public interface KafkaProducerService {
    void send(String topic, String message);
    void send(String topic, String key, String message);
}
