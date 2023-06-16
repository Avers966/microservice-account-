package ru.skillbox.diplom.group35.microservice.account.api.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSecureDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountStatisticResponseDto;

import java.util.UUID;

@FeignClient(
        name = "accountFeignClient",
        url = "http://microservice-account",
//        url = "http://localhost:8082",
        path = "/api/v1/account")
public interface AccountFeignClient{

    @GetMapping("/statistic")
    ResponseEntity<AccountStatisticResponseDto> getAccountStatistic(@SpringQueryMap
                                                                    AccountStatisticRequestDto statisticRequestDto);

    @GetMapping("/me")
    ResponseEntity<AccountDto> get();

    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getById(@PathVariable(name = "id") UUID id);

    @GetMapping
    ResponseEntity<AccountSecureDto> getByEmail(@RequestParam("email") String email);

    @PostMapping
    ResponseEntity<AccountDto> create(@RequestBody AccountDto dto);

    @PutMapping(value = "/me")
    ResponseEntity<AccountDto> update(@RequestBody AccountDto dto);

    @PutMapping
    ResponseEntity<AccountDto> updateByEmail(@RequestBody AccountDto dto);

    @DeleteMapping(value = "/me")
    void delete();

    @DeleteMapping(value = "/{id}")
    void deleteById(@PathVariable(name = "id") UUID id);

    @PutMapping("/birthdays")
    ResponseEntity sendBirthdayNotification();
}
