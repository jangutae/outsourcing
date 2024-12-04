package com.example.outsourcing.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "사용자 이름 혹은 ID 값은 필수입니다."),
    PASSWORD_INCORRECT(HttpStatus.UNAUTHORIZED,"비밀번호가 올바르지 않습니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"변경할 비밀번호는 기존 비밀번호와 같지 않아야 합니다"),

    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST,"중복된 이메일입니다"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST,"잘못된 형식의 이메일입니다."),

    DEACTIVATED_USER(HttpStatus.FORBIDDEN,"탈퇴한 사용자입니다."),


    ALREADY_LOGINED_USER(HttpStatus.BAD_REQUEST,"이미 로그인 되어있습니다" );

    private final HttpStatus httpStatus;
    private final String message;
}
