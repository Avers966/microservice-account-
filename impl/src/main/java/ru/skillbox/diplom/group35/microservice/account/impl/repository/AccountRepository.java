package ru.skillbox.diplom.group35.microservice.account.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.annotation.EnableBaseRepository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.account.api.domain.Account;

import java.util.Optional;
import java.util.UUID;

/**
 * AccountController
 *
 * @author Georgii Vinogradov
 */

@Repository
public interface AccountRepository extends BaseRepository<Account> {
}
