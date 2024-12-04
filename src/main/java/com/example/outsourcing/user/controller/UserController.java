package com.example.outsourcing.user.controller;

import com.example.outsourcing.user.dto.SignupRequestDto;
import com.example.outsourcing.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.singUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>("회원 가입 성공", HttpStatus.CREATED);
    }
}
