package ru.skillbox.diplom.group35.microservice.account.api.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Role;

import java.util.List;

@Getter
@Setter
public class AccountSecureDto extends BaseDto {

    private String email;
    private String password;
    private List<Role> roles;
}
