package com.example.outsourcing.order.dto;

import com.example.outsourcing.order.enums.DeliveryState;

public record UpdateDeliveryStateRequestDto(
        Long storeId,
        DeliveryState state) {
}
