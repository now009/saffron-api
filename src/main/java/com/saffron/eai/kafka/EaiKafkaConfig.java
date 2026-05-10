package com.saffron.eai.kafka;

import com.saffron.api.kafka.KafkaToggleProperties;
import com.saffron.eai.common.EaiMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
public class EaiKafkaConfig {

    private final KafkaToggleProperties props;

    public EaiKafkaConfig(KafkaToggleProperties props) {
        this.props = props;
    }

    @Bean
    public ProducerFactory<String, EaiMessage> eaiProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, EaiMessage> eaiKafkaTemplate() {
        return new KafkaTemplate<>(eaiProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, EaiMessage> eaiConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "eai-workflow-engine");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.saffron.eai.*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, EaiMessage.class.getName());
        return new DefaultKafkaConsumerFactory<>(config);
    }

    // MANUAL_IMMEDIATE ack — EaiMessageConsumer의 Acknowledgment와 맞춤
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EaiMessage> eaiKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EaiMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(eaiConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public KafkaAdmin.NewTopics eaiKafkaTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("eai.interface.request").partitions(1).replicas(1).build(),
                TopicBuilder.name("eai.interface.response").partitions(1).replicas(1).build(),
                TopicBuilder.name("eai.interface.dlq").partitions(1).replicas(1).build()
        );
    }
}
