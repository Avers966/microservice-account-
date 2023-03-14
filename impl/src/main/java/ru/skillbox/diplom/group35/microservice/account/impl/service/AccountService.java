package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.account.api.domain.Account;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AccountRepository;


import java.util.Optional;
import java.util.UUID;

/**
 * service
 *
 * @author Denis_Kholmogorov
 */
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private Account dtoToAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setIsDeleted(accountDto.getIsDeleted());
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEmail(accountDto.getEmail());
        account.setPassword(accountDto.getPassword());
        account.setPhone(accountDto.getPhone());
        account.setPhoto(accountDto.getPhoto());
        account.setAbout(accountDto.getAbout());
        account.setCity(accountDto.getCity());
        account.setCountry(accountDto.getCountry());
        account.setStatusCode(accountDto.getStatusCode());
        account.setRegDate(accountDto.getRegDate());
        account.setBirthDate(accountDto.getBirthDate());
        account.setMessagePermission(accountDto.getMessagePermission());
        account.setLastOnlineTime(accountDto.getLastOnlineTime());
        account.setIsOnline(accountDto.getIsOnline());
        account.setIsBlocked(accountDto.getIsBlocked());
        account.setPhotoId(accountDto.getPhotoId());
        account.setPhotoName(accountDto.getPhotoName());
        account.setCreatedOn(accountDto.getCreatedOn());
        account.setUpdatedOn(accountDto.getUpdatedOn());
        return account;
    }

    private AccountDto accountToDto(Account account){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setIsDeleted(account.getIsDeleted());
        accountDto.setFirstName(account.getFirstName());
        accountDto.setLastName(account.getLastName());
        accountDto.setEmail(account.getEmail());
        accountDto.setPassword(account.getPassword());
        accountDto.setPhone(account.getPhone());
        accountDto.setPhoto(account.getPhoto());
        accountDto.setAbout(account.getAbout());
        accountDto.setCountry(accountDto.getCountry());
        accountDto.setStatusCode(account.getStatusCode());
        accountDto.setRegDate(account.getRegDate());
        accountDto.setBirthDate(account.getBirthDate());
        accountDto.setMessagePermission(account.getMessagePermission());
        accountDto.setLastOnlineTime(account.getLastOnlineTime());
        accountDto.setIsOnline(account.getIsOnline());
        accountDto.setIsBlocked(account.getIsBlocked());
        accountDto.setPhotoId(account.getPhotoId());
        accountDto.setPhotoName(account.getPhotoName());
        accountDto.setCreatedOn(account.getCreatedOn());
        accountDto.setUpdatedOn(account.getUpdatedOn());
        return accountDto;
    }

    public ResponseEntity<AccountDto> getById(UUID id) {
        Optional<Account> finder = accountRepository.findById(id);
        return finder.isPresent() ?
                ResponseEntity.ok().body(accountToDto(finder.get())) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<AccountDto> create(AccountDto dto) {
        Account account = dtoToAccount(dto);
        account = accountRepository.save(account);
        return ResponseEntity.ok().body(accountToDto(account));
    }

}
