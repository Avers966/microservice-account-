package ru.skillbox.diplom.group35.microservice.account.impl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.account.api.dto.IAccountCountPerAge;
import ru.skillbox.diplom.group35.microservice.account.api.dto.StatPerMonth;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * AccountController
 *
 * @author Georgii Vinogradov
 */

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    @Query(value =
            "SELECT DATE_PART('year', age(:date, a.birth_date)) AS age, COUNT(*) AS count " +
                    "FROM account a " +
                    "LEFT JOIN \"user\" u  ON a.id = u.id " +
                    "WHERE (a.birth_date <= :date OR a.birth_date is NULL) AND (u.created_on <= :date)  " +
                    "GROUP BY age",
            nativeQuery = true)
    List<IAccountCountPerAge> equalOrLessThen(@Param("date") ZonedDateTime dateTime);


    @Query("SELECT " +
            "new ru.skillbox.diplom.group35.microservice.account.api.dto.StatPerMonth(" +
            "cast(DATE_TRUNC('month', u.createdOn) as timestamp), cast(count(u.createdOn) as integer)) " +
            "FROM User u " +
            "WHERE " +
            "u.createdOn >= DATE_TRUNC('month', cast(:firstMonth as timestamp)) AND " +
            "u.createdOn < DATE_TRUNC('month', cast(:lastMonth as timestamp)) " +
            "GROUP BY DATE_TRUNC('month', u.createdOn)")
    List<StatPerMonth> getStatPerMonth(@Param("firstMonth") ZonedDateTime dateFirst,
                                       @Param("lastMonth") ZonedDateTime dateLast);

    List<Account> findAccountByIsDeleted(Boolean bool);

    List<Account> findAccountByEmail(String email);

    @Query(value = "SELECT * FROM Account a " +
            "JOIN \"user\" u  ON a.id = u.id " +
            "WHERE " +
                "(date_part('month', current_timestamp) = 3 AND date_part('day', current_timestamp) = 1) " +
                "AND " +
                "(date_part('day', a.birth_date) = 29 AND mod(cast(date_part('year', current_timestamp) as integer), 4) <> 0)" +
            "OR " +
                "date_part('month', a.birth_date) = date_part('month', current_timestamp) " +
                "AND " +
                "date_part('day', a.birth_date) = date_part('day', current_timestamp)"
            , nativeQuery = true)
    List<Account> findByBirthdayToday();
}
