package ru.skillbox.diplom.group35.microservice.account.api.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
import ru.skillbox.diplom.group35.microservice.account.api.dto.*;

import java.util.UUID;

/**
 * AccountController
 *
 * @author Georgii Vinogradov
 */

@Tag(name = "Account controller", description = "Работа с аккаунтами")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@RequestMapping(value = "/api/v1/account", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AccountController extends BaseController<AccountDto, AccountSearchDto> {

    @GetMapping("/statistic")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение статистики", description = "Позволяет получить статистику по кол-ву регистраций " +
            "по возрастам и по кол-ву регистраций по месяцам за указанный промежуток времени")
    ResponseEntity<AccountStatisticResponseDto> getAccountStatistic(
            @SpringQueryMap AccountStatisticRequestDto statisticRequestDto);

    @GetMapping("/me")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение данных аккаунта", description = "Получение своих данных при входе на сайт")
    ResponseEntity<AccountDto> get(@RequestHeader("Authorization") String bearerToken);

    @Override
    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение данных аккаунта", description = "Получение данных по id")
    ResponseEntity<AccountDto> getById(@PathVariable(name = "id") UUID id);

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение данных аккаунта", description = "Получение данных по email")
    ResponseEntity<AccountSecureDto> getByEmail(@RequestHeader("Authorization") String bearerToken, @RequestParam("email") String email);

    @GetMapping("/search")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение списка аккаунтов", description = "Позволяет получить список аккаунтов по " +
            "заданным параметрам с использованием спецификации")
    ResponseEntity<Page<AccountDto>> search(AccountSearchDto searchDto, Pageable pageable);

    @GetMapping("/search/statusCode")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение списка аккаунтов относительно запрашиваемого статуса",
               description = "Позволяет получать аккаунты относительно запрашиваемого статуса")
    ResponseEntity<Page<AccountDto>> searchByStatusCode(AccountSearchDto searchDto, Pageable pageable);

    @Override
    @GetMapping("/unsupported")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение всех аккаунтов", description = "Позволяет получить все аккаунты, не реализован")
    ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto, Pageable pageable);

    @Override
    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создание аккаунта", description = "Создание аккаунта при регистрации")
    ResponseEntity<AccountDto> create(@RequestBody AccountDto dto);

    @PutMapping(value = "/me")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление аккаунта", description = "Обновление авторизованного аккаунта")
    ResponseEntity<AccountDto> update(@RequestHeader("Authorization") String bearerToken, @RequestBody AccountDto dto);

    @Override
    @PutMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление аккаунта", description = "Обновление аккаунта")
    ResponseEntity<AccountDto> update(@RequestBody AccountDto dto);

    @DeleteMapping(value = "/me")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удаление аккаунта", description = "Помечает авторизованный аккаунт как удалённый " +
            "и через заданное время стирает данные об аккаунте")
    void delete(@RequestHeader("Authorization") String bearerToken);

    @Override
    @DeleteMapping(value = "/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удаление аккаунта", description = "Полность удаляет аккаунт из базы по id")
    void deleteById(@PathVariable(name = "id") UUID id);

    @PutMapping("/birthdays")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Отправка уведомлений", description = "Отправляет друзьям сообщение о наступившем дне рождении")
    ResponseEntity sendBirthdayNotification();
}
