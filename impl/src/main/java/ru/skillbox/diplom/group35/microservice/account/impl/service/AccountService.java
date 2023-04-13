package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.library.core.utils.SecurityUtil;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSearchDto;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account_;
import ru.skillbox.diplom.group35.microservice.account.impl.mapper.AccountMapper;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AccountRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.UUID;

import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.*;

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
    private final SecurityUtil securityUtil;


    public Page<AccountDto> search(AccountSearchDto searchDto, Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(getSpecByAllFields(searchDto), pageable);
        return accounts.map(accountMapper::mapToDto);
    }

    public AccountDto get() {
        return getById(securityUtil.getAccountDetails().getId());
    }

    public AccountDto getById(UUID id) {
        return accountMapper.mapToDto(accountRepository.getById(id));
    }

    public AccountDto getByEmail(String email) {
        Account account = accountRepository
                .findOne(getSpecByEmail(email))
                .orElseThrow(EntityNotFoundException::new);
        return accountMapper.mapToDtoWithPass(account);
    }

    public AccountDto create(AccountDto dto) {
        Account account = accountRepository.save(accountMapper.mapToAccount(dto));
        return accountMapper.mapToDto(account);
    }

    public AccountDto update(AccountDto accountDto) {
        Account extractAccount = accountRepository.getById(securityUtil.getAccountDetails().getId());
        Account updatedAccount = accountMapper.updateAccount(accountDto, extractAccount);
        return accountMapper.mapToDto(accountRepository.save(updatedAccount));
    }

    public void delete() {
        accountRepository.deleteById(securityUtil.getAccountDetails().getId());
    }

    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }

    public static Specification<Account> getSpecByAllFields(AccountSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                .and(in(Account_.id, searchDto.getIds(), true))
                .and(notIn(Account_.id, searchDto.getBlockedByIds(), true))
                .and(equal(Account_.firstName, searchDto.getFirstName(), true))
                .and(equal(Account_.lastName, searchDto.getLastName(), true))
                .and(likeLowerCase(Account_.firstName, searchDto.getFirstName(), true))
                .and(likeLowerCase(Account_.lastName, searchDto.getLastName(), true))
                .and(between(Account_.birthDate,
                        searchDto.getAgeTo() == null ? null : ZonedDateTime.now().minusYears(searchDto.getAgeTo()),
                        searchDto.getAgeFrom() == null ? null : ZonedDateTime.now().minusYears(searchDto.getAgeFrom()),
                        true));
    }

    private static Specification<Account> getSpecByEmail(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

    public Integer getAccountCount() {
        return Math.toIntExact(accountRepository.count());
    }



}
