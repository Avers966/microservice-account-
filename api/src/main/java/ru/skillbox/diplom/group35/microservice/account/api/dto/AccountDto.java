package ru.skillbox.diplom.group35.microservice.account.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * AccountDto
 *
 * @author Denis_Kholmogorov
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AccountDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String photo;
    private String profileCover;
    private String about;
    private String city;
    private String country;
    private StatusCode statusCode;
    private ZonedDateTime regDate;
    private ZonedDateTime birthDate;
    private String messagePermission;
    private ZonedDateTime lastOnlineTime;
    private Boolean isOnline;
    private Boolean isBlocked;
    private String emojiStatus;
    private ZonedDateTime createdOn;
    private ZonedDateTime updatedOn;
    private ZonedDateTime deletionTimestamp;

    public AccountDto(UUID id, Boolean isDeleted, ZonedDateTime deletionTimestamp) {
        super(id, isDeleted);
        this.deletionTimestamp = deletionTimestamp;
    }
}
