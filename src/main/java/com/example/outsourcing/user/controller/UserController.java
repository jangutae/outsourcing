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
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 요청을 처리하는 컨트롤러 클래스.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입 요청을 처리하는 메소드.
     * @param requestDto 회원 가입 요청 정보를 담고 있는 DTO
     * @return 회원 가입 성공 메시지와 상태 코드
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.singUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword(), requestDto.getRole());
        return new ResponseEntity<>("회원 가입을 성공했습니다.", HttpStatus.CREATED);
    }

    /**
     * 로그인 요청을 처리하는 메소드.
     * @param loginRequestDto 로그인 요청 정보를 담고 있는 DTO
     * @param request HttpServletRequest 객체
     * @return 로그인 성공 메시지와 상태 코드
     * @throws CustomException 이미 로그인된 사용자인 경우 예외 발생
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){
        User loginUser = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        HttpSession session = request.getSession();
        if (session.getAttribute("id") != null) {
            throw new CustomException(UserErrorCode.ALREADY_LOGINED_USER);
        }

        session.setAttribute("id", loginUser.getId());
        return new ResponseEntity<>("로그인을 성공 했습니다. ", HttpStatus.OK);
    }

    /**
     * 로그아웃 요청을 처리하는 메소드.
     * @param request HttpServletRequest 객체
     * @return 로그아웃 성공 메시지와 상태 코드
     */
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return new ResponseEntity<>("로그아웃이 되었습니다.", HttpStatus.OK);
    }

    /**
     * 회원 탈퇴 요청을 처리하는 메소드.
     * @param id 세션에서 가져온 사용자 ID
     * @param request HttpServletRequest 객체
     * @return 회원 탈퇴 성공 메시지와 상태 코드
     */
    @DeleteMapping("/leave")
    public ResponseEntity<String> leave(@SessionAttribute("id") Long id, HttpServletRequest request){
        userService.leave(id);
        log.info("{}", id);

        // 탈퇴 완료 후, 로그아웃 처리
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        return ResponseEntity.ok().body("회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
    }
}
