package ru.skillbox.diplom.group35.microservice.account.impl.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSearchDto;
import ru.skillbox.diplom.group35.microservice.account.impl.service.AccountService;
import ru.skillbox.diplom.group35.microservice.account.api.resource.AccountController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * AccountControllerImpl
 *
 * @author Georgii Vinogradov
 */

@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;


    @Override
    public ResponseEntity<AccountDto> get(@RequestHeader("Authorization") String token) {
        return accountService.get(new AccountDto());
    }

    @Override
    public ResponseEntity<AccountDto> getById(@PathVariable(name = "id") UUID id) {
        return accountService.getById(id);
    }

    @Override
    public ResponseEntity<Page<AccountDto>> search(AccountSearchDto searchDto,
                                                   Pageable pageable) {
        return accountService.search(searchDto, pageable);
    }

    @Override
    public ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto,
                                                   Pageable page) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto) {
        return accountService.create(dto);
    }

    @Override
    public ResponseEntity<AccountDto> update(@RequestHeader("Authorization") String token) {
        return accountService.update(new AccountDto());
    }

    @Override
    public ResponseEntity<AccountDto> update(@RequestBody AccountDto dto) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public void delete(@RequestHeader("Authorization") String token) {
        accountService.delete(new AccountDto());
    }

    @Override
    public void deleteById(@PathVariable(name = "id") UUID id) {
        accountService.deleteById(id);
    }
}
