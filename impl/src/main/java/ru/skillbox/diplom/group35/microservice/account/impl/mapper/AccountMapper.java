package ru.skillbox.diplom.group35.microservice.account.impl.mapper;

import org.mapstruct.*;
import ru.skillbox.diplom.group35.library.core.dto.statistic.StatisticPerDateDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountSecureDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.StatPerMonth;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, AuthorityMapper.class})
public interface AccountMapper {
    @Mapping(target = "password", ignore = true)
    AccountDto mapToDto(Account account);
    @Mapping(target = "isDeleted", ignore = true)
    AccountSecureDto mapToSecureDto(Account account);

    AccountDto mapToDtoWithPass(Account account);

    Account mapToAccount(AccountDto accountDto);

    @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    StatisticPerDateDto mapToStatisticPerDateDto(StatPerMonth statPerMonth);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account updateAccount(AccountDto accountDto, @MappingTarget Account account);
}
