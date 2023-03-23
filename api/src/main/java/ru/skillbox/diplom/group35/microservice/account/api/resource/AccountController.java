package ru.skillbox.diplom.group35.microservice.account.api.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;
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

    @GetMapping("/me")
    ResponseEntity<AccountDto> get(String authorizationHeader);

    @Override
    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getById(UUID uuid);

    @GetMapping("/search")
    ResponseEntity<Page<AccountDto>> search(AccountSearchDto searchDto, Pageable pageable);

    @Override
    @GetMapping
    ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto, Pageable pageable);

    @Override
    @PostMapping
    ResponseEntity<AccountDto> create(AccountDto dto);

    @PutMapping(value = "/me")
    ResponseEntity<AccountDto> update(String authorizationHeader);

    @Override
    @PutMapping
    ResponseEntity<AccountDto> update(AccountDto dto);

    @DeleteMapping(value = "/me")
    void delete(String authorizationHeader);

    @Override
    @DeleteMapping(value = "/{id}")
    void deleteById(UUID uuid);
}
