package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.StatusCode;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AccountRepository;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * RemovalService
 *
 * @author Grigory Dyakonov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RemovalService {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private static final String PHOTO_TO_DELETE = "http://res.cloudinary.com/da0wr9y51/image/" +
            "upload/v1686504522/del_photo.jpg";
    private static final int DAYS_BEFORE_ACCOUNT_DELETION = 1;
    @Scheduled(cron="0 0 0 * * ?") // в полночь
    protected void removeAccountsIsDeleted() {
        List<Account> listAccountIsDeleted = accountRepository
                .findAccountByIsDeleted(true);
        log.info("Number of accounts to delete: " + listAccountIsDeleted.size());
        listAccountIsDeleted.forEach(account -> {
            ZonedDateTime deletionTime = account.getDeletionTimestamp();
            if (deletionTime.until(ZonedDateTime.now(), ChronoUnit.DAYS) >= DAYS_BEFORE_ACCOUNT_DELETION) {
                AccountDto accountDto = new AccountDto()
                        .setEmail("")
                        .setFirstName("User deleted")
                        .setLastName("")
                        .setAbout("The account has been deleted")
                        .setCity("")
                        .setCountry("")
                        .setEmojiStatus("")
                        .setStatusCode(StatusCode.NONE)
                        .setPhone("")
                        .setCreatedOn(null)
                        .setLastOnlineTime(null)
                        .setMessagePermission("")
                        .setUpdatedOn(null)
                        .setPassword("")
                        .setRegDate(null)
                        .setBirthDate(null)
                        .setDeletionTimestamp(ZonedDateTime.now())
                        .setIsOnline(false)
                        .setProfileCover("")
                        .setPhoto(PHOTO_TO_DELETE);
                accountService.update(accountDto);
                log.info("Account id: {}, firstName: {}, lastName: {} was deleted",
                        account.getId(), account.getFirstName(), account.getLastName());
            }
        }
        );
    }

}
