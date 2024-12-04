package com.example.outsourcing.user.repository;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.UserErrorCode;
import com.example.outsourcing.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findByUserOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(()-> new CustomException(UserErrorCode.NOT_FOUND));
    }

    default User findByIdOrElseThrows(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));
    }

    Optional<User> findUserByRoleAndId(AccountRole accountRole, Long id);

    default User findUserByRoleAndIdOrElseThrow(AccountRole accountRole,Long id) {
        return findUserByRoleAndId(accountRole,id).orElseThrow(()->new CustomException(UserErrorCode.NOT_FOUND));
    }
}
