package com.example.outsourcing.user.service;

import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import com.example.outsourcing.common.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public  void singUp(String name, String email, String password) {

        String hashedPassword = PasswordEncoder.encode(password);

        User user = new User(name, email, hashedPassword);

        userRepository.save(user);
    }
}
