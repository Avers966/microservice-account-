package ru.skillbox.diplom.group35.microservice.account.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import ru.skillbox.diplom.group35.microservice.account.api.domain.Account;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {
    @Mapping(target = "password", ignore = true)
    public abstract AccountDto mapToDto(Account account);

    @AfterMapping
    protected void clearPassword(@MappingTarget AccountDto accountDto) {
        accountDto.setPassword("");
    }

    public abstract Account mapToAccount(AccountDto accountDto);
}