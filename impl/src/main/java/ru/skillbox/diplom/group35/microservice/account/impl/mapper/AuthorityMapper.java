package ru.skillbox.diplom.group35.microservice.account.impl.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AuthorityDto;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Authority;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    List<AuthorityDto> map(List<Authority> roles);
}
