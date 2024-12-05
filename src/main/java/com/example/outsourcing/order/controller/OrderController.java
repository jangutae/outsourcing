package com.example.outsourcing.order.controller;

import com.example.outsourcing.order.dto.OrderResponseDto;
import com.example.outsourcing.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession userSession = httpServletRequest.getSession(false);
        Long userId = (Long) userSession.getAttribute("id");

        OrderResponseDto orderResponseDto = orderService.createdOrder(userId, storeId, menuId);

        return ResponseEntity.ok().body(orderResponseDto);
    }
}
