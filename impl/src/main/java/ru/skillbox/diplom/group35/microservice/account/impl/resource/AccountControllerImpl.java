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

@Getter
@Setter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    // todo: добавить получение по токену
    @Override
    @GetMapping("/me")
    public ResponseEntity<AccountDto> get() {
        return ResponseEntity.ok(new AccountDto());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getById(@PathVariable(name = "id") UUID id) {
        return accountService.getById(id);
    }

    // todo: реализовать search
    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<AccountDto>> search(@RequestBody AccountSearchDto searchDto,
                                                   @RequestBody Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AccountDto>> getAll(@RequestBody AccountSearchDto searchDto,
                                                   @RequestBody Pageable page) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto) {
        return accountService.create(dto);
    }

    // todo: добавить update по токену
    @Override
    @PutMapping(value = "/me")
    public ResponseEntity<AccountDto> update() {
        return null;
    }

    @Override
    @PutMapping
    public ResponseEntity<AccountDto> update(@RequestBody AccountDto dto) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    // todo: добавить удаление по токену
    @Override
    @DeleteMapping(value = "/me")
    public void delete() {
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable(name = "id") UUID id) {
    }
}
