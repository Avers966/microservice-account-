package ru.skillbox.diplom.group35.microservice.account.domain;

import javax.persistence.*;

/**
 * Account
 *
 * @author Denis_Kholmogorov
 */
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
