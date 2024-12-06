package com.example.outsourcing.user.service;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.constants.AccountStatus;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.UserErrorCode;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import com.example.outsourcing.common.util.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입 메소드.
     * @param name 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @param role 사용자 역할
     * @throws CustomException 이메일 중복 또는 탈퇴한 회원인 경우 예외 발생
     */
    public void singUp(String name, String email, String password, AccountRole role) {
        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isPresent()) {
            // 탈퇴한 회원 회원가입 시 예외처리
            if (AccountStatus.NOT_USE.equals(findUser.get().getState())) {
                throw new CustomException(UserErrorCode.DEACTIVATED_USER);
            }
            throw new CustomException(UserErrorCode.DUPLICATED_EMAIL);
        }

        String hashedPassword = PasswordEncoder.encode(password);
        User user = new User(name, email, hashedPassword, role, AccountStatus.USE);
        userRepository.save(user);
    }

    /**
     * 로그인 메소드.
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 사용자 엔티티
     * @throws CustomException 탈퇴한 회원이거나 비밀번호가 틀린 경우 예외 발생
     */
    public User login(String email, String password) {
        User findUser = userRepository.findByUserOrElseThrow(email);

        // 탈퇴한 회원 로그인 예외처리
        if (AccountStatus.NOT_USE.equals(findUser.getState())) {
            throw new CustomException(UserErrorCode.DEACTIVATED_USER);
        }

        if (!PasswordEncoder.matches(password, findUser.getPassword())) {
            throw new CustomException(UserErrorCode.PASSWORD_INCORRECT);
        }

        return findUser;
    }

    /**
     * 회원 탈퇴 메소드.
     * @param id 사용자 ID
     * @throws CustomException 사용자가 없을 경우 예외 발생
     */
    @Transactional
    public void leave(Long id) {
        User findUser = userRepository.findByIdOrElseThrows(id);
        findUser.disableUserAccount(AccountStatus.NOT_USE);
    }

    /**
     * 특정 역할을 가진 사용자를 ID로 조회하는 메소드.
     * @param id 사용자 ID
     * @return 사용자 엔티티
     * @throws CustomException 사용자가 없을 경우 예외 발생
     */
    public User findOwnerById(Long id) {
        return userRepository.findUserByRoleAndIdOrElseThrow(AccountRole.BOSS, id);
    }
}
