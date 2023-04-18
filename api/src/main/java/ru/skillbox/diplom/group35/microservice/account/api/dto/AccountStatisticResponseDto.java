package ru.skillbox.diplom.group35.microservice.account.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AccountStatisticResponseDto
 *
 * @author Grigory Dyakonov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatisticResponseDto extends StatisticPerDateDto {
    private List<AccountCountPerAge> countPerAge;
    private List<StatisticPerDateDto> countPerMonth;
}
