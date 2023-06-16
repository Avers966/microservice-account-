package ru.skillbox.diplom.group35.microservice.account.impl.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * ConstConfig
 *
 * @author Grigory Dyakonov
 */

@Data
@Configuration
public class ConstConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.max.poll.interval.ms}")
    private Integer maxPollInterval;

    @Value("${spring.kafka.consumer.spring.json.trusted.packages}")
    private String trustPackages;

    @Value("${spring.kafka.consumer.concurrency}")
    private Integer concurrency;

    @Value("${spring.kafka.topic.account}")
    private String topic;

    @Value("${spring.kafka.topic.birthday-notification}")
    private String birthdayEventTopic;

    @Value("${spring.kafka.topic.partition-count}")
    private Integer partitionCount;

    @Value("${spring.kafka.topic.replication-factor}")
    private Short replicationFactor;

    @Value("${spring.kafka.producer.retries}")
    private Integer retries;
}
