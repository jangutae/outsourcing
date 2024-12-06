package com.example.outsourcing.user.repository;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.UserErrorCode;
import com.example.outsourcing.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 엔티티와 관련된 데이터베이스 작업을 처리하는 레포지토리 인터페이스.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일을 기준으로 사용자 정보를 조회하는 메소드.
     * @param email 사용자 이메일
     * @return Optional 형태의 사용자 엔티티
     */
    Optional<User> findByEmail(String email);

    /**
     * 이메일을 기준으로 사용자를 조회하고, 없을 경우 예외를 던지는 메소드.
     * @param email 사용자 이메일
     * @return 사용자 엔티티
     * @throws CustomException 사용자가 없을 경우 예외 발생
     */
    default User findByUserOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));
    }

    /**
     * ID를 기준으로 사용자를 조회하고, 없을 경우 예외를 던지는 메소드.
     * @param id 사용자 ID
     * @return 사용자 엔티티
     * @throws CustomException 사용자가 없을 경우 예외 발생
     */
    default User findByIdOrElseThrows(Long id) {
        return findById(id).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));
    }

    /**
     * 역할과 ID를 기준으로 사용자 정보를 조회하는 메소드.
     * @param accountRole 사용자 역할
     * @param id 사용자 ID
     * @return Optional 형태의 사용자 엔티티
     */
    Optional<User> findUserByRoleAndId(AccountRole accountRole, Long id);

    /**
     * 역할과 ID를 기준으로 사용자를 조회하고, 없을 경우 예외를 던지는 메소드.
     * @param accountRole 사용자 역할
     * @param id 사용자 ID
     * @return 사용자 엔티티
     * @throws CustomException 사용자가 없을 경우 예외 발생
     */
    default User findUserByRoleAndIdOrElseThrow(AccountRole accountRole, Long id) {
        return findUserByRoleAndId(accountRole, id).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));
    }
}
