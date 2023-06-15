package ru.skillbox.diplom.group35.microservice.account.impl.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.skillbox.diplom.group35.library.core.dto.streaming.EventNotificationDto;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaProduserConfig
 *
 * @author Grigory Dyakonov
 */

@Configuration
@RequiredArgsConstructor

public class KafkaProduserConfig {
    private final ConstConfig constConfig;

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, constConfig.getBootstrapAddress());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.RETRIES_CONFIG, constConfig.getRetries());
        return properties;
    }

    @Bean
    public ProducerFactory<String, EventNotificationDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, EventNotificationDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
