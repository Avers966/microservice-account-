package ru.skillbox.diplom.group35.microservice.account.impl.mapper;

import org.mapstruct.*;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "password", ignore = true)
    AccountDto mapToDto(Account account);

    AccountDto mapToDtoWithPass(Account account);

    Account mapToAccount(AccountDto accountDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account updateAccount(AccountDto accountDto, @MappingTarget Account account);
}
