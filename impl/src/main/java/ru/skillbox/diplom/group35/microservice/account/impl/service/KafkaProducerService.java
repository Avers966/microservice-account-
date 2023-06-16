package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.library.core.dto.streaming.EventNotificationDto;
import ru.skillbox.diplom.group35.microservice.account.impl.kafka.ConstConfig;

/**
 * KafkaProducerService
 *
 * @author Grigory Dyakonov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, EventNotificationDto> kafkaTemplate;
    private final ConstConfig constConfig;

    public void send(EventNotificationDto eventNotificationDto){
        log.info("sent message authorId: {} to topic {}", eventNotificationDto.getAuthorId(), constConfig.getBirthdayEventTopic());
        kafkaTemplate.send(constConfig.getBirthdayEventTopic(), constConfig.getBirthdayEventTopic(), eventNotificationDto);
    }

}
