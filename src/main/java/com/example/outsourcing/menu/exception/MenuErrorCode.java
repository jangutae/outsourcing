package com.example.outsourcing.menu.exception;

import com.example.outsourcing.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND,"가게 및 메뉴 정보를 조회할 수 없습니다."),
    NOT_ACCESS(HttpStatus.FORBIDDEN, "본인이 생성한 가게만 접근할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
