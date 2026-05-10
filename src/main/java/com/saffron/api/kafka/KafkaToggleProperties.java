package com.saffron.api.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaToggleProperties {

    private boolean enabled = false;
    private String bootstrapServers = "localhost:9092";
    private Consumer consumer = new Consumer();
    private Producer producer = new Producer();

    public static class Consumer {
        private String groupId = "saffron-api";
        private String topic = "saffron-topic";

        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }
    }

    public static class Producer {
        private String topic = "saffron-topic";

        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getBootstrapServers() { return bootstrapServers; }
    public void setBootstrapServers(String bootstrapServers) { this.bootstrapServers = bootstrapServers; }
    public Consumer getConsumer() { return consumer; }
    public void setConsumer(Consumer consumer) { this.consumer = consumer; }
    public Producer getProducer() { return producer; }
    public void setProducer(Producer producer) { this.producer = producer; }
}
