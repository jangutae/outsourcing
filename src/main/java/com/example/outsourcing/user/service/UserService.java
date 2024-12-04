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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public  void singUp(String name, String email, String password, AccountRole role) {

        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isPresent()) {

            // 탈퇴한 회원 회원가입 시 예외처리
            if (AccountStatus.NOT_USE.equals(findUser.get().getState())) {
                throw new CustomException(UserErrorCode.NOT_FOUND);
            }

            throw new CustomException(UserErrorCode.DUPLICATED_EMAIL);
        }

        String hashedPassword = PasswordEncoder.encode(password);

        User user = new User(name, email, hashedPassword, role, AccountStatus.USE);

        userRepository.save(user);
    }

    public User login(String email, String password) {

        User findUser = userRepository.findByUserOrElseThrow(email);

        // 탈퇴한 회원 로그인 예외처리
        if (AccountStatus.NOT_USE.equals(findUser.getState())) {
            throw new CustomException(UserErrorCode.DEACTIVATED_USER);
        }

        if(!PasswordEncoder.matches(password, findUser.getPassword())){
            throw new CustomException(UserErrorCode.PASSWORD_INCORRECT);
        }

        return findUser;
    }

    @Transactional
    public void leave(Long id) {
        User findUser = userRepository.findByIdOrElseThrows(id);
        findUser.disableUserAccount(AccountStatus.NOT_USE);
    }

    public User findOwnerById(Long id) {
        return userRepository.findUserByRoleAndIdOrElseThrow(AccountRole.BOSS,id);
    }
}
