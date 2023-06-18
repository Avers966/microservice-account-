package ru.skillbox.diplom.group35.microservice.account.impl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.library.core.dto.statistic.StatisticPerDateDto;
import ru.skillbox.diplom.group35.library.core.dto.streaming.EventNotificationDto;
import ru.skillbox.diplom.group35.library.core.utils.SecurityUtil;
import ru.skillbox.diplom.group35.microservice.account.api.dto.*;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account_;
import ru.skillbox.diplom.group35.microservice.account.impl.mapper.AccountCountPerAgeMapper;
import ru.skillbox.diplom.group35.microservice.account.impl.mapper.AccountMapper;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AccountRepository;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.AuthorityRepository;
import ru.skillbox.diplom.group35.microservice.account.impl.repository.RoleRepository;
import ru.skillbox.diplom.group35.microservice.friend.feignclient.FriendFeignClient;
import ru.skillbox.diplom.group35.microservice.notification.feignclient.NotificationFeignClient;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.*;

/**
 * service
 *
 * @author Denis_Kholmogorov
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountCountPerAgeMapper accountCountPerAgeMapper;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final SecurityUtil securityUtil;
    private final FriendFeignClient friendFeignClient;
    private final NotificationFeignClient notificationFeignClient;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    private final KafkaProducerService producerService;


    public AccountStatisticResponseDto getAccountStatistic(AccountStatisticRequestDto statisticRequestDto) {
        List<IAccountCountPerAge> countPerAgesList = accountRepository.equalOrLessThen(statisticRequestDto.getDate());
        List<AccountCountPerAge> countPerAges = accountCountPerAgeMapper.map(countPerAgesList);

        List<StatPerMonth> statisticPerMonthList = accountRepository.getStatPerMonth(
                statisticRequestDto.getFirstMonth(), statisticRequestDto.getLastMonth().plusMonths(1));
        List<StatisticPerDateDto> statisticPerDateDtoList = statisticPerMonthList.stream()
                .map(accountMapper::mapToStatisticPerDateDto)
                .collect(Collectors.toList());
        ZonedDateTime dateTime = statisticRequestDto.getDate();
        Integer count = countPerAges.stream().map(AccountCountPerAge::getCount).reduce(0, Integer::sum);

        return new AccountStatisticResponseDto(dateTime, count, countPerAges, statisticPerDateDtoList);
    }


    public Page<AccountDto> search(AccountSearchDto searchDto, Pageable pageable) {
        Page<AccountDto> accountDtoPage = accountRepository
                .findAll(getSpecByAllFields(excludeBlockedUsers(searchDto)), pageable)
                .map(accountMapper::mapToDto);
        log.info("find accounts without blocked and deleted users");

        Map<UUID, String> foundFriendStatus = getRelationshipStatuses(accountDtoPage);
        if (!foundFriendStatus.isEmpty()) {
            settingStatuses(foundFriendStatus, accountDtoPage);
        }
        log.info("received a list of accounts with relationships");
        return accountDtoPage;
    }

    public Page<AccountDto> searchByStatusCode(AccountSearchDto searchDto, Pageable pageable) {
        List<UUID> accountListWithStatus = friendFeignClient.statusFriend(
                String.valueOf(searchDto.getStatusCode())).getBody();
        searchDto.setIds(accountListWithStatus);
        Page<Account> accounts = accountRepository.findAll(getSpecByAllFields(searchDto), pageable);
        Page<AccountDto> accountDtoPage = accounts.map(accountMapper::mapToDto);
        accountDtoPage.stream()
                .forEach(accountDto -> accountDto.setStatusCode(searchDto.getStatusCode()));
        return accountDtoPage;
    }

    public AccountDto get() {
        return getById(securityUtil.getAccountDetails().getId());
    }

    public AccountDto getById(UUID id) {
        AccountDto accountDto = accountMapper.mapToDto(accountRepository.getById(id));
        log.info("got account with id: {}, name: {}", accountDto.getId(), accountDto.getFirstName());
        if (!securityUtil.getAccountDetails().getId().equals(id)) {
            Map<UUID, String> relationshipMap = friendFeignClient.checkFriend(List.of(id)).getBody();
            if (!relationshipMap.isEmpty()) {
                accountDto.setStatusCode(StatusCode.valueOf(relationshipMap.get(id)));
                log.info("set statusCode - {}", accountDto.getStatusCode());
            }
        }
        return accountDto;
    }

    public AccountSecureDto getByEmail(String email) {
        Account account = accountRepository
                .findOne(getSpecByEmail(email))
                .orElseThrow(EntityNotFoundException::new);
        return accountMapper.mapToSecureDto(account);
    }

    public AccountDto create(AccountDto dto) {
        Account account = accountMapper.mapToAccount(dto);
        account.setRoles(roleRepository.getUserRoles());
        account.setAuthorities(authorityRepository.findAll());
        accountRepository.save(account);
        ResponseEntity<Boolean> resultCreateSettings = notificationFeignClient.createSetting(account.getId());
        if (!resultCreateSettings.getBody()) {
            log.info("account creation error");
            throw new RuntimeException();
        }
        log.info("account creation successful");
        return accountMapper.mapToDto(account);
    }

    public AccountDto update(AccountDto accountDto) {
        Account extractAccount = accountRepository.getById(securityUtil.getAccountDetails().getId());
        Account updatedAccount = accountMapper.updateAccount(accountDto, extractAccount);
        return accountMapper.mapToDto(accountRepository.save(updatedAccount));
    }

    public AccountDto updateByEmail(AccountDto accountDto) {
        Account extractAccount = accountRepository
                .findAccountByEmail(accountDto.getEmail())
                .stream().findFirst().get();
        Account updatedAccount = accountMapper.updateAccount(accountDto, extractAccount);
        return accountMapper.mapToDto(accountRepository.save(updatedAccount));
    }

    public void updateStatusOnline(AccountDto accountDto) {
        Account extractAccount = accountRepository.getById(accountDto.getId());
        log.info("current status online - {}", extractAccount.getIsOnline());
        Account updatedAccount = accountMapper.updateAccount(accountDto, extractAccount);
        accountRepository.save(updatedAccount);
    }

    public void updateOnDeletion(AccountDto accountDto) {
        log.info("call updateOnDeletion");
        Account extractAccount = accountRepository.getById(accountDto.getId());
        Account updatedAccount = accountMapper.updateAccount(accountDto, extractAccount);
        accountRepository.save(updatedAccount);
    }

    public void delete() {
        accountRepository.deleteById(securityUtil.getAccountDetails().getId());
    }

    public void softRemoval() {
        update(new AccountDto(securityUtil.getAccountDetails().getId(),
                              true,
                              ZonedDateTime.now()));
    }

    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }

    public ResponseEntity sendBirthdayNotification() {
        List<Account> accountList = accountRepository.findByBirthdayToday();
        log.info("Found {} records whose birthday is today", accountList.size());
        List<EventNotificationDto> eventNotificationDtoList = new ArrayList<>();
        accountList.forEach(account -> eventNotificationDtoList.add(new EventNotificationDto()
                    .setAuthorId(account.getId())
                    .setNotificationType("FRIEND_BIRTHDAY")
                    .setContent("У " + account.getFirstName() + " " + account.getLastName() +
                            " сегодня ДР. Не забудьте поздравить! Номер карты для поздравления: ********* :)"))
        );
        eventNotificationDtoList.forEach(eventNotificationDto -> producerService.send(eventNotificationDto));
        return ResponseEntity.ok().build();
    }

    public static Specification<Account> getSpecByAllFields(AccountSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                .and(in(Account_.id, searchDto.getIds(), true))
                .and(notIn(Account_.id, searchDto.getBlockedByIds(), true))
                .and(likeLowerCase(Account_.firstName, searchDto.getFirstName(), true))
                .and(likeLowerCase(Account_.lastName, searchDto.getLastName(), true))
                .and(equal(Account_.country, searchDto.getCountry(), true))
                .and(equal(Account_.city, searchDto.getCity(), true))
                .and(between(Account_.birthDate,
                        searchDto.getAgeTo() == null ? null : ZonedDateTime.now().minusYears(searchDto.getAgeTo()),
                        searchDto.getAgeFrom() == null ? null : ZonedDateTime.now().minusYears(searchDto.getAgeFrom()),
                        true));
    }

    private static Specification<Account> getSpecByEmail(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

    private AccountSearchDto excludeBlockedUsers(AccountSearchDto searchDto) {
        List<UUID> blockedByIdsList = friendFeignClient.getBlockFriendId().getBody();
        log.info("got a list of blocked users from friendFeignClient");
        blockedByIdsList.add(securityUtil.getAccountDetails().getId());
        searchDto.setBlockedByIds(blockedByIdsList);
        return searchDto;
    }

    private Map<UUID, String> getRelationshipStatuses(Page<AccountDto> accountDtoPage) {
        List<UUID> ids = accountDtoPage.stream()
                .map(AccountDto::getId)
                .collect(Collectors.toList());
        Map<UUID, String> foundFriendStatus = friendFeignClient.checkFriend(ids).getBody();
        log.info("got relationship statuses from friendFeignClient");
        log.info("number of requested relationships = {}", foundFriendStatus.size());
        return foundFriendStatus;
    }

    private Page<AccountDto> settingStatuses(Map<UUID, String> foundFriendStatus, Page<AccountDto> accountDtoPage) {
        accountDtoPage.stream()
                .filter(dto -> foundFriendStatus.containsKey(dto.getId()))
                .forEach(dto -> {
                    StatusCode statusCode = StatusCode.valueOf(foundFriendStatus.get(dto.getId()));
                    dto.setStatusCode(statusCode);
                    log.info("set new status id:{} from:{} to:{}", dto.getId(), dto.getStatusCode(), statusCode);
                });
        return accountDtoPage;
    }

}
