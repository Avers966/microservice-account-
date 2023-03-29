package ru.skillbox.diplom.group35.microservice.account.impl.repository;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;

/**
 * AccountController
 *
 * @author Georgii Vinogradov
 */

@Repository
public interface AccountRepository extends BaseRepository<Account> {
}
