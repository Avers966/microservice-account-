package ru.skillbox.diplom.group35.microservice.account.api.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


public interface AccountController extends BaseController<AccountDto, AccountSearchDto> {

    ResponseEntity<AccountDto> get();

    @Override
    ResponseEntity<AccountDto> getById(UUID uuid);

    ResponseEntity<Page<AccountDto>> search(AccountSearchDto searchDto, Pageable pageable);

    @Override
    ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto, Pageable pageable);

    @Override
    ResponseEntity<AccountDto> create(AccountDto dto);

    ResponseEntity<AccountDto> update();

    @Override
    ResponseEntity<AccountDto> update(AccountDto dto);

    void delete();

    @Override
    void deleteById(UUID uuid);
}
