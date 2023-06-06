package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;

/**
 * KafkaConsumerService
 *
 * @author Grigory Dyakonov
 */

@Slf4j
@Service
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerService extends AbstractConsumerSeekAware {
    private final AccountService accountService;

    @KafkaListener(topics = "${spring.kafka.topic.account}")
    public void receiveStatusOnLine(ConsumerRecord<String, AccountDto> record, Acknowledgment acknowledgment) {
        log.info("consumer service activate");
        accountService.updateStatusOnline(record.value());
        acknowledgment.acknowledge();
        log.info("set status onLine: {} for account-id: {}", record.value().getIsOnline(), record.value().getId());
    }

}
