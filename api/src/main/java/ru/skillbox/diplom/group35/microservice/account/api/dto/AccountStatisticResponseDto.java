package ru.skillbox.diplom.group35.microservice.account.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group35.library.core.dto.statistic.StatisticPerDateDto;

import java.time.ZonedDateTime;
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

    public AccountStatisticResponseDto(ZonedDateTime date, Integer count, List<AccountCountPerAge> countPerAge, List<StatisticPerDateDto> countPerMonth) {
        super(date, count);
        this.countPerAge = countPerAge;
        this.countPerMonth = countPerMonth;
    }
}
