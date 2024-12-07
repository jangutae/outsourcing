package com.example.outsourcing.order.dto;

import com.example.outsourcing.order.entity.Order;
import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryRequestDto(

        @NotNull(message = "주문상태 변경은 필수값 입니다.")
        Order.DeliveryState state
) {
}
