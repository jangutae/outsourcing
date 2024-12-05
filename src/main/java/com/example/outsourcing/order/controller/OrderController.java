package com.example.outsourcing.order.controller;

import com.example.outsourcing.menu.service.MenuService;
import com.example.outsourcing.order.dto.OrderRequestDto;
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
    @PostMapping("/stores/{storeId}/menus/{menuId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody OrderRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        OrderResponseDto orderResponseDto = orderService.createdOrder(userId, menuId, storeId ,requestDto);

        return ResponseEntity.ok().body(orderResponseDto);
    }

    // 주문 상태 변경
    @PatchMapping("/stores/{storeId}/menus/{menuId}/orders/{orderId}")
    public ResponseEntity<String> updateDeliveryState(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @PathVariable Long orderId,
            @RequestBody UpdateDeliveryStateRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        String deliveryState = orderService.updatedDeliveryState(userId, storeId, menuId, orderId, requestDto);

        return ResponseEntity.ok().body("주문 상태가 " + deliveryState + " 로 변경되었습니다.");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> findAllOrderByUserId(HttpServletRequest httpServletRequest) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        List<OrderResponseDto> orderResponseDto = orderService.findAllOrderByUserId(userId);

        return ResponseEntity.ok().body(orderResponseDto);
    }
}
