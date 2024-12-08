package com.example.outsourcing.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND,"해당 리뷰가 없습니다."),
    INVALID_NOT_ME(HttpStatus.BAD_REQUEST,"내가 주문한 건에만 리뷰를 작성할 수 있습니다."),
    INVALID_OWNER(HttpStatus.BAD_REQUEST, "사용자만 리뷰를 작성할 수 있습니다. 사장님 안돼요"),
    INVALID_OTHER_STATE(HttpStatus.BAD_REQUEST,"배달완료후에 리뷰를 작성할 수 있습니다."),
    INVALID_NO_STORE_OR_MENU(HttpStatus.BAD_REQUEST, "해당 상점 혹은 메뉴가 존재하지 않습니다. ");

    private final HttpStatus httpStatus;
    private final String message;
}
