package ru.skillbox.diplom.group35.microservice.account.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dto аккаунта")
public class AccountDto extends BaseDto {
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Почтовый адрес")
    private String email;
    @Schema(description = "Пароль")
    private String password;
    @Schema(description = "Номер телефона")
    private String phone;
    @Schema(description = "Ссылка на cloudinary")
    private String photo;
    @Schema(description = "Ссылка на cloudinary")
    private String profileCover;
    @Schema(description = "Информация о себе")
    private String about;
    @Schema(description = "Город")
    private String city;
    @Schema(description = "Страна")
    private String country;
    @Schema(description = "Статус отношений")
    private StatusCode statusCode;
    @Schema(description = "Дата регистрации")
    private ZonedDateTime regDate;
    @Schema(description = "Дата рождения")
    private ZonedDateTime birthDate;
    @Schema(description = "Какая-то ерунда")
    private String messagePermission;
    @Schema(description = "Время последнего посещения")
    private ZonedDateTime lastOnlineTime;
    @Schema(description = "Статус онлайн - true/false")
    private Boolean isOnline;
    @Schema(description = "Заблокирован ли пользователь для отображения при поиске")
    private Boolean isBlocked;
    @Schema(description = "Статус эмодзи")
    private String emojiStatus;
    @Schema(description = "Дата регистрации")
    private ZonedDateTime createdOn;
    @Schema(description = "Дата обновления аккаунта")
    private ZonedDateTime updatedOn;
    @Schema(description = "Время пометки на удаление для \"мягкого\" удаления")
    private ZonedDateTime deletionTimestamp;

    public AccountDto(UUID id, Boolean isDeleted, ZonedDateTime deletionTimestamp) {
        super(id, isDeleted);
        this.deletionTimestamp = deletionTimestamp;
    }
}
