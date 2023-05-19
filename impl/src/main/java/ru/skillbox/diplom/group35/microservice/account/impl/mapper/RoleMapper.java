package ru.skillbox.diplom.group35.microservice.account.impl.mapper;


import org.mapstruct.Mapper;
import ru.skillbox.diplom.group35.microservice.account.api.dto.RoleDto;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    List<RoleDto> map(List<Role> roles);
}
