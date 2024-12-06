package com.example.outsourcing.order.dto;

import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.order.enums.DeliveryState;

public record OrderResponseDto(
        Long id,
        Long userId,
        Long menuId,
        Integer orderPrice,
        DeliveryState state) {

    public static OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getUser().getId(),
                order.getMenu().getId(),
                order.getOrderPrice(),
                order.getState());
    }
}
