package ru.skillbox.diplom.group35.microservice.account.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Account;
import ru.skillbox.diplom.group35.microservice.account.domain.model.Role;

import java.util.List;
import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("select r from Role r where r.role like 'ROLE_USER'")
    List<Role> getUserRoles();

}
