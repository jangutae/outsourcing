package com.example.outsourcing.user.controller;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.UserErrorCode;
import com.example.outsourcing.user.dto.LoginRequestDto;
import com.example.outsourcing.user.dto.SignupRequestDto;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.singUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>("회원 가입 성공", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){

        User loginUser = userService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );

        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            throw new CustomException(UserErrorCode.ALREADY_LOGINED_USER);
        }

        session.setAttribute("id", loginUser.getId());

        return new ResponseEntity<>("로그인이 성공 했습니다. ",HttpStatus.OK);
    }
}
