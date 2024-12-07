package com.example.outsourcing.order.dto;

import com.example.outsourcing.order.entity.Order;

public record OrderResponseDto(
        Long id,
        Long userId,
        Long menuId,
        String storeName,
        String menuName,
        Order.DeliveryState state) {

    public static OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getUser().getId(),
                order.getMenu().getId(),
                order.getStore().getStoreName(),
                order.getMenu().getMenuName(),
                order.getState());
    }
}
