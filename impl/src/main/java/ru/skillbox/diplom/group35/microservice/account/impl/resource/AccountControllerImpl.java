package ru.skillbox.diplom.group35.microservice.account.impl.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.library.core.annotation.EnableExceptionHandler;
import ru.skillbox.diplom.group35.microservice.account.api.dto.*;
import ru.skillbox.diplom.group35.microservice.account.api.resource.AccountController;
import ru.skillbox.diplom.group35.microservice.account.impl.service.AccountService;

import java.util.UUID;

/**
 * AccountControllerImpl
 *
 * @author Georgii Vinogradov
 */

@Slf4j
@RestController
@Transactional
@EnableExceptionHandler
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountStatisticResponseDto> getAccountStatistic(AccountStatisticRequestDto statisticRequestDto) {
        log.info("call get account count method");
        return ResponseEntity.ok(accountService.getAccountStatistic(statisticRequestDto));
    }

    @Override
    public ResponseEntity<AccountDto> get(String bearerToken) {
        log.info("call method get with token - " + bearerToken);
        return ResponseEntity.ok(accountService.get());
    }

    @Override
    public ResponseEntity<AccountDto> getById(UUID id) {
        log.info("call getById with id: {}", id);
        return ResponseEntity.ok(accountService.getById(id));
    }

    @Override
    public ResponseEntity<AccountSecureDto> getByEmail(String bearerToken, String email) {
        log.info("call getByEmail: {}", email);
        return ResponseEntity.ok(accountService.getByEmail(email));
    }

    @Override
    public ResponseEntity<Page<AccountDto>> search(AccountSearchDto searchDto, Pageable pageable) {
        log.info("call search");
        return ResponseEntity.ok(accountService.search(searchDto, pageable));
    }

    @Override
    public ResponseEntity<Page<AccountDto>> searchByStatusCode(AccountSearchDto searchDto, Pageable pageable) {
        log.info("call searchByStatusCode");
        return ResponseEntity.ok(accountService.searchByStatusCode(searchDto, pageable));
    }

    @Override
    public ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto, Pageable page) {
        log.info("call getAll");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<AccountDto> create(AccountDto dto) {
        log.info("call create");
        return ResponseEntity.ok(accountService.create(dto));
    }

    @Override
    public ResponseEntity<AccountDto> update(String bearerToken, AccountDto dto) {
        log.info("call update with token: {}", bearerToken);
        return ResponseEntity.ok(accountService.update(dto));
    }

    @Override
    public ResponseEntity<AccountDto> update(AccountDto dto) {
        log.info("call update by Email");
        return ResponseEntity.ok(accountService.updateByEmail(dto));
    }

    @Override
    public void delete(String bearerToken) {
        log.info("call delete with token: {}", bearerToken);
        accountService.softRemoval();
    }

    @Override
    public void deleteById(@PathVariable(name = "id") UUID id) {
        log.info("call delete with id: {}", id);
        accountService.deleteById(id);
    }

    @Override
    public ResponseEntity sendBirthdayNotification() {
        log.info("call BirthDayNotification");
        return accountService.sendBirthdayNotification();
    }
}
