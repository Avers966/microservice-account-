package ru.skillbox.diplom.group35.microservice.account.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.skillbox.diplom.group35.library.core.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * User
 *
 * @author Georgii Vinogradov
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "is_online", nullable = false)
    private boolean isDeleted;

    @Column(name = "first_name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String lastName;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = false)
    private String email;

    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "updated_on", nullable = false)
    private ZonedDateTime updatedOn;
}
