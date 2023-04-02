package ru.skillbox.diplom.group35.microservice.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.skillbox.diplom.group35.library.core.annotation.EnableBaseRepository;
import ru.skillbox.diplom.group35.library.core.annotation.EnableSecurity;
import ru.skillbox.diplom.group35.microservice.account.api.client.AccountFeignClient;

/**
 * Application
 *
 * @author Denis_Kholmogorov
 */


@EnableSecurity
@EnableBaseRepository
@EnableFeignClients(basePackages = "ru.skillbox.diplom.group35.microservice.account.api.client")
@SpringBootApplication
//        (exclude = {SecurityAutoConfiguration.class,
//        ManagementWebSecurityAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
