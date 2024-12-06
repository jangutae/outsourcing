package com.example.outsourcing.order.controller;

import com.example.outsourcing.order.dto.OrderResponseDto;
import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.order.service.OrderService;
import com.example.outsourcing.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/menus/{menuId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable Long menuId,
            @SessionAttribute(value = "id") User user
    ) {
        OrderResponseDto orderResponseDto = orderService.createdOrder(user.getId(), menuId);

        return ResponseEntity.ok().body(orderResponseDto);
    }

    // 주문 상태 변경
    @PatchMapping("/menus/{menuId}/orders/{orderId}")
    public ResponseEntity<String> updateDeliveryState(
            @PathVariable Long menuId,
            @PathVariable Long orderId,
            @RequestBody @NotNull(message = "주문상태값은 필수값 입니다.") Order.DeliveryState state,
            @SessionAttribute(value = "id") User user
    ) {
        String deliveryState = orderService.updatedDeliveryState(user.getId(), menuId, orderId, state);

        return ResponseEntity.ok().body("주문 상태가 " + deliveryState + " 로 변경되었습니다.");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> findAllOrderByUserId(@SessionAttribute(value = "id") User user) {
        List<OrderResponseDto> orderResponseDto = orderService.findAllOrderByUserId(user.getId());

        return ResponseEntity.ok().body(orderResponseDto);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> checkDeliveryState(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = orderService.checkedDeliveryState(orderId);

        return ResponseEntity.ok().body(orderResponseDto);
    }
}
