package ru.skillbox.diplom.group35.microservice.account.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * StatisticRequestDto
 *
 * @author Grigory Dyakonov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticRequestDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime firstMonth;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime lastMonth;
}
