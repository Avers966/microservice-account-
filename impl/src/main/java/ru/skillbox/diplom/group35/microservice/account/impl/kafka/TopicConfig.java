package ru.skillbox.diplom.group35.microservice.account.impl.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * TopicConfig
 *
 * @author Grigory Dyakonov
 */

@Configuration
@RequiredArgsConstructor
public class TopicConfig {
    private final ConstConfig config;

    @Bean
    public Map<String, Object> adminConfig() {
        Map<String, Object> configuration = new HashMap<>();
        configuration.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapAddress());
        return configuration;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(adminConfig());
    }

    @Bean
    public NewTopic accountTopic() {
        return new NewTopic(config.getTopic(),
                            config.getPartitionCount(),
                            config.getReplicationFactor());
    }
}
