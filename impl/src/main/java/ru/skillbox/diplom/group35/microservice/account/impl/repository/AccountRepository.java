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
//    @Query("SELECT " +
//            "new ru.skillbox.diplom.group35.microservice.account.api.dto.AccountCountPerAge(" +
//            "CAST(extract(year from age(a.birthDate)) as integer), CAST(COUNT(a.birthDate) as integer))" +
//            "FROM Account a WHERE a.birthDate <= :date  OR a.birthDate is NULL " +
//            "GROUP BY a.birthDate"
//    )
//    List<AccountCountPerAge> equalOrLessThen(@Param("date") ZonedDateTime dateTime);

    @Query(value =
            "SELECT DATE_PART('year', age(:date, a.birth_date)) AS age, COUNT(*) AS count " +
            "FROM account a " +
            "WHERE a.birth_date <= :date OR a.birth_date is NULL " +
            "GROUP BY age",
            nativeQuery = true)
    List<IAccountCountPerAge> equalOrLessThen(@Param("date") ZonedDateTime dateTime);

//    @Query("SELECT " +
//            "new ru.skillbox.diplom.group35.microservice.account.api.dto.StatPerMonth(" +
//            "cast(DATE_TRUNC('month', a.regDate) as timestamp), cast(count(a.regDate) as integer)) " +
//            "FROM Account a " +
//            "WHERE " +
//            "a.regDate >= DATE_TRUNC('month', cast(:firstMonth as timestamp)) AND " +
//            "a.regDate < DATE_TRUNC('month', cast(:lastMonth as timestamp)) " +
//            "GROUP BY DATE_TRUNC('month', a.regDate)")
//    List<StatPerMonth> getStatPerMonth(@Param("firstMonth") ZonedDateTime dateFirst,
//                                       @Param("lastMonth") ZonedDateTime dateLast);

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
}
