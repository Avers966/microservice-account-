package ru.skillbox.diplom.group35.microservice.account.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * StatisticPerDateDto
 *
 * @author Grigory Dyakonov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticPerDateDto {
    private ZonedDateTime date;
    private Integer count;
}
