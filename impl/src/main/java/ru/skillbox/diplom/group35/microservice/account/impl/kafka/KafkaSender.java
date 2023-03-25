package ru.skillbox.diplom.group35.microservice.account.impl.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Sender
 *
 * @author Georgii Vinogradov
 */

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic.account}")
    private String topic;
    public void send(String msgId, String message){
        kafkaTemplate.send(topic, msgId,  message);
    }
}