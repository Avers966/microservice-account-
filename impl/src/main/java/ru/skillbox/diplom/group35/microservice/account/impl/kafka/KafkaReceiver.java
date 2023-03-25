package ru.skillbox.diplom.group35.microservice.account.impl.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableKafka
public class KafkaReceiver {

//    @KafkaListener(topics = "${app.topic.account}")
    public void receive(String message) {
        log.info("received message='{}'", message);
    }

}
