package com.example.outsourcing.order.service;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.UserErrorCode;
import com.example.outsourcing.menu.repository.MenuRepository;
import com.example.outsourcing.order.dto.OrderResponseDto;
import com.example.outsourcing.order.repository.OrderRepository;
import com.example.outsourcing.store.repository.StoreRepository;
import com.example.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public OrderResponseDto createdOrder(Long userId, Long storeId, Long menuId) {
        userRepository.findByIdOrElseThrows(userId);
        // 변경 필요 에러 코드
        storeRepository.findById(storeId).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));

    }
}
