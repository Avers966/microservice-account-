package ru.skillbox.diplom.group35.microservice.account.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSearchDto;

import java.util.UUID;

@FeignClient(
        name = "microservice-account",
//        url = "http://localhost:8080",
        path = "/api/v1/account")
public interface AccountFeignClient{

    @GetMapping("/statistic")
    ResponseEntity<Integer> getAccountCount();

    @GetMapping("/me")
    ResponseEntity<AccountDto> get(@RequestHeader("Authorization") String bearerToken);

    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getById(@PathVariable(name = "id") UUID id);

    @PostMapping
    ResponseEntity<AccountDto> create(@RequestBody AccountDto dto);

    @PutMapping(value = "/me")
    ResponseEntity<AccountDto> update(@RequestHeader("Authorization") String bearerToken, @RequestBody AccountDto dto);

    @PutMapping
    ResponseEntity<AccountDto> update(@RequestBody AccountDto dto);

    @DeleteMapping(value = "/me")
    void delete(@RequestHeader("Authorization") String bearerToken);

    @DeleteMapping(value = "/{id}")
    void deleteById(@PathVariable(name = "id") UUID id);
}
