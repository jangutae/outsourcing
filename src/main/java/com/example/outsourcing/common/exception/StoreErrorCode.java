package com.example.outsourcing.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {
    OUT_RANGE(HttpStatus.BAD_REQUEST,"가게는 3개까지 만들 수 있습니다 ."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"가게를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
