package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.skillbox.diplom.group35.microservice.account.api.domain.Account;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSearchDto;
import ru.skillbox.diplom.group35.microservice.account.impl.mapper.AccountMapper;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AccountRepository;


import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * service
 *
 * @author Denis_Kholmogorov
 */

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public ResponseEntity<AccountDto> get(AccountDto accountDto) {
        return getById(accountDto.getId());
    }

    public ResponseEntity<Page<AccountDto>> search(AccountSearchDto searchDto,
                                            Pageable pageable) {
        return null;
    }

    public ResponseEntity<AccountDto> getById(UUID id) {
        Optional<Account> finder = accountRepository.findById(id);
        return finder.isPresent() ?
                ResponseEntity.ok().body(accountMapper.mapToDto(finder.get())) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<AccountDto> create(AccountDto dto) {
        Account account = accountRepository
                .save(accountMapper.mapToAccount(dto));
        return ResponseEntity.ok().body(accountMapper.mapToDto(account));
    }

    public ResponseEntity<AccountDto> update(AccountDto accountDto) {
        Account account = accountRepository
                .save(accountMapper.mapToAccount(accountDto));
        return ResponseEntity.ok().body(accountMapper.mapToDto(account));
    }

    public void delete(AccountDto accountDto) {
        deleteById(accountDto.getId());
    }

    public void deleteById(UUID id) {
        accountRepository.findById(id)
                .stream()
                .forEach(p -> {
                    p.setIsDeleted(true);
                    accountRepository.save(p);
                });
    }

}
