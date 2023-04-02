package ru.skillbox.diplom.group35.microservice.account.api.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSearchDto;

import java.util.UUID;

/**
 * AccountController
 *
 * @author Georgii Vinogradov
 */


@RequestMapping("/api/v1/account")
public interface AccountController extends BaseController<AccountDto, AccountSearchDto> {

    @GetMapping("/statistic")
    ResponseEntity<Integer> getAccountCount();

    @GetMapping("/me")
    ResponseEntity<AccountDto> get(@RequestHeader("Authorization") String bearerToken);

    @Override
    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getById(@PathVariable(name = "id") UUID id);

    @GetMapping("/search")
    ResponseEntity<Page<AccountDto>> search(AccountSearchDto searchDto, Pageable pageable);

    @Override
    @GetMapping
    ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto, Pageable pageable);

    @Override
    @PostMapping
    ResponseEntity<AccountDto> create(@RequestBody AccountDto dto);

    @PutMapping(value = "/me")
    ResponseEntity<AccountDto> update(@RequestHeader("Authorization") String bearerToken, @RequestBody AccountDto dto);

    @Override
    @PutMapping
    ResponseEntity<AccountDto> update(@RequestBody AccountDto dto);

    @DeleteMapping(value = "/me")
    void delete(@RequestHeader("Authorization") String bearerToken);

    @Override
    @DeleteMapping(value = "/{id}")
    void deleteById(@PathVariable(name = "id") UUID id);
}
