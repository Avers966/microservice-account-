package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSearchDto;
import ru.skillbox.diplom.group35.microservice.account.impl.exception.UnauthorizedException;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account_;
import ru.skillbox.diplom.group35.microservice.account.impl.mapper.AccountMapper;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AccountRepository;


import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
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

    private Account extractAccount() {
        Account account = null;
        try {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = token.getToken().getClaim("email").toString();
            account = accountRepository.findOne(getSpecByEmail(email)).get();
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        return account;
    }

    public Page<AccountDto> search(AccountSearchDto searchDto, Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(getSpecByAllFields(searchDto), pageable);
        return accounts.map(accountMapper::mapToDto);
    }

    public AccountDto get() {
        return accountMapper.mapToDto(extractAccount());
    }

    public AccountDto getById(UUID id) {
        return accountMapper.mapToDto(accountRepository.getById(id));
    }

    public AccountDto create(AccountDto dto) {
        Account account = accountRepository.save(accountMapper.mapToAccount(dto));
        return accountMapper.mapToDto(account);
    }

    public AccountDto update(AccountDto accountDto) {
        accountDto.setId(extractAccount().getId());
        Account account = accountRepository.save(accountMapper.mapToAccount(accountDto));
        return accountMapper.mapToDto(account);
    }

    public void delete() {
        accountRepository.delete(extractAccount());
    }

    public void deleteById(UUID id) {
        accountRepository.findById(id).ifPresent(p -> {
            p.setIsDeleted(true);
            accountRepository.save(p);
        });
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
