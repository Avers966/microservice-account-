package ru.skillbox.diplom.group35.microservice.account.impl.mapper;

import org.mapstruct.*;
import ru.skillbox.diplom.group35.microservice.account.api.domain.Account;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "password", expression = "java(\"\")")
    AccountDto mapToDto(Account account);

    Account mapToAccount(AccountDto accountDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account updateAccount(AccountDto accountDto, @MappingTarget Account account);
}