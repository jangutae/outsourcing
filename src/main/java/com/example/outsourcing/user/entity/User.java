package com.example.outsourcing.user.entity;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.constants.AccountStatus;
import com.example.outsourcing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus state;

    public User(String name, String email, String password, AccountRole role, AccountStatus state) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
    }

    public void disableUserAccount(AccountStatus accountStatus) {
        this.state = accountStatus;
    }

}
