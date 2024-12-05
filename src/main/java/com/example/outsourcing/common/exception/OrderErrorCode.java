package com.example.outsourcing.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {


    NOT_ACCESS_BOSS(HttpStatus.UNAUTHORIZED,"사장님은 접근할 수 없습니다."),

    NOT_ACCESS_USER(HttpStatus.UNAUTHORIZED, "사용자는 접근할 수 없습니다."),

    OPEN_YET(HttpStatus.UNAUTHORIZED,"영업시간 전 입니다."),

    ALREADY_CLOSE(HttpStatus.BAD_REQUEST,"영업시간 종료 되었습니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND,"요청하신 정보를 찾을 수 없습니다."),

    MIN_PRICE_NOT_SATISFIED(HttpStatus.BAD_REQUEST,"최소 주문 금액을 충족하지 못했습니다."),

    DEACTIVATED_USER(HttpStatus.FORBIDDEN,"탈퇴한 사용자입니다.");


    private final HttpStatus httpStatus;
    private final String message;

}