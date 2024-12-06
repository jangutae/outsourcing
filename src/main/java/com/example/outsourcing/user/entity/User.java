package com.example.outsourcing.user.entity;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.constants.AccountStatus;
import com.example.outsourcing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 엔티티 클래스.
 */
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

    /**
     * 새로운 사용자 객체를 생성하는 생성자.
     * @param name 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @param role 사용자 역할
     * @param state 사용자 상태
     */
    public User(String name, String email, String password, AccountRole role, AccountStatus state) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
    }

    /**
     * 사용자 계정을 비활성화하는 메소드.
     * @param accountStatus 비활성화할 상태
     */
    public void disableUserAccount(AccountStatus accountStatus) {
        this.state = accountStatus;
    }
}
