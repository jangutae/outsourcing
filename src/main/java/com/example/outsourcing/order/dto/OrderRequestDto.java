package com.example.outsourcing.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderRequestDto(

        @NotNull(message = "가게 고유 식별자는 필수값 입니다.")
        Long storeId,

        @NotNull(message = "주문 금액은 필수값 입니다.")
        Integer orderPrice) {

}
