package ru.skillbox.diplom.group35.microservice.account.impl.service;

import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.account.api.dto.AccountDto;
import ru.skillbox.diplom.group35.microservice.account.domain.Account;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;

/**
 * service
 *
 * @author Denis_Kholmogorov
 */
@Service
public class ServiceTest {

    @PostConstruct()
    private void init()
    {
        BaseDto baseDto = new BaseDto();
        baseDto.setDeleted(true);
        System.out.println(baseDto);


        AccountDto accountDto = new AccountDto();
        accountDto.setDeleted(true);
        System.out.println(accountDto);
    }

}
