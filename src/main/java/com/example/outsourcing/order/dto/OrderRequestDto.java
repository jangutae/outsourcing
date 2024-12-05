package com.example.outsourcing.order.dto;

public record OrderRequestDto(
        Long storeId,
        Integer orderPrice) {

}
