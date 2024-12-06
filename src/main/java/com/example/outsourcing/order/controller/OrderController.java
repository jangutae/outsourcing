package com.example.outsourcing.order.controller;

import com.example.outsourcing.order.dto.OrderResponseDto;
import com.example.outsourcing.order.dto.UpdateDeliveryStateRequestDto;
import com.example.outsourcing.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
            HttpServletRequest httpServletRequest
    ) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        OrderResponseDto orderResponseDto = orderService.createdOrder(userId, menuId);

        return ResponseEntity.ok().body(orderResponseDto);
    }

    // 주문 상태 변경
    @PatchMapping("/menus/{menuId}/orders/{orderId}")
    public ResponseEntity<String> updateDeliveryState(
            @PathVariable Long menuId,
            @PathVariable Long orderId,
            @RequestBody UpdateDeliveryStateRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        String deliveryState = orderService.updatedDeliveryState(userId, menuId, orderId, requestDto);

        return ResponseEntity.ok().body("주문 상태가 " + deliveryState + " 로 변경되었습니다.");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> findAllOrderByUserId(HttpServletRequest httpServletRequest) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        List<OrderResponseDto> orderResponseDto = orderService.findAllOrderByUserId(userId);

        return ResponseEntity.ok().body(orderResponseDto);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> checkDeliveryState(
            @PathVariable Long orderId,
            HttpServletRequest httpServletRequest) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        OrderResponseDto orderResponseDto = orderService.checkedDeliveryState(orderId, userId);

        return ResponseEntity.ok().body(orderResponseDto);
    }
}
