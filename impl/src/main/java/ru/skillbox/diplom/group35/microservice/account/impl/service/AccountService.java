package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSearchDto;
import ru.skillbox.diplom.group35.microservice.account.impl.mapper.AccountMapper;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AccountRepository;


import javax.transaction.Transactional;
import java.util.UUID;

/**
 * service
 *
 * @author Denis_Kholmogorov
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountDto get(AccountDto accountDto) {
        return getById(accountDto.getId());
    }

    public Page<AccountDto> search(AccountSearchDto searchDto, Pageable pageable) {
        return null;
    }

    public AccountDto getById(UUID id) {
        return accountMapper.mapToDto(accountRepository.getById(id));
    }

    public AccountDto create(AccountDto dto) {
        Account account = accountRepository.save(accountMapper.mapToAccount(dto));
        return accountMapper.mapToDto(account);
    }

    public AccountDto update(AccountDto accountDto) {
        Account account = accountRepository.save(accountMapper.mapToAccount(accountDto));
        return accountMapper.mapToDto(account);
    }

    public void delete(AccountDto accountDto) {
    }

    public void deleteById(UUID id) {
        accountRepository.findById(id).ifPresent(p -> {
            p.setIsDeleted(true);
            accountRepository.save(p);
        });
    }

}
