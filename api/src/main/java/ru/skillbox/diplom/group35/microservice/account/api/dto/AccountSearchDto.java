package ru.skillbox.diplom.group35.microservice.account.api.dto;

import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;

import java.util.List;
import java.util.UUID;

/**
 * AccountSearchDto
 *
 * @author Georgii Vinogradov
 */

@Getter
@Setter
public class AccountSearchDto extends BaseSearchDto {
    private List<UUID> ids;
    private List<UUID> blockedByIds;
    private String author;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private Boolean isBlocked;
    private StatusCode statusCode;
    private Integer ageTo;
    private Integer ageFrom;
}
