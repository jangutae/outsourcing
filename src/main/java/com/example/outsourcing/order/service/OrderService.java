package com.example.outsourcing.order.service;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.OrderErrorCode;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.repository.MenuRepository;
import com.example.outsourcing.order.dto.OrderResponseDto;
import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.order.repository.OrderRepository;
import com.example.outsourcing.store.repository.StoreRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public OrderResponseDto createdOrder(Long userId, Long menuId) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

        Order createdOrder = new Order(user, menu.getStore(), menu, Order.DeliveryState.ORDER_COMPLETE);
//
        // 사용자만 음식을 주문할 수 있습니다.
        if (!menu.isBossAccessPossible(user)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_BOSS);
        }

        // 최소주문 금액을 만족해야 합니다.
        if (!createdOrder.isOverMinPrice(menu)) {
            throw new CustomException(OrderErrorCode.MIN_PRICE_NOT_SATISFIED);
        }

        // 오픈 시간 아닙니다.
        if (createdOrder.isNotOpened(menu)) {
            throw new CustomException(OrderErrorCode.OPEN_YET);
        }

        // 마감 시간이 지났습니다.
        if (createdOrder.isAlreadyClosed(menu)) {
            throw new CustomException(OrderErrorCode.ALREADY_CLOSE);
        }

        orderRepository.save(createdOrder);

        return OrderResponseDto.toDto(createdOrder);
    }

    @Transactional
    public String updatedDeliveryState(Long userId, Long menuId, Long orderId, Order.DeliveryState state) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);
        Order order = orderRepository.findOrderByIdAndState(orderId, state);

        if (menu.isBossAccessPossible(user)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_USER);
        }

        order.setState(state);
        orderRepository.save(order);

        return order.getState().toString();
    }

    public List<OrderResponseDto> findAllOrderByUserId(Long userId) {
        List<Order> orderByUserId = orderRepository.findOrderByUserId(userId);

        return orderByUserId.stream().map(OrderResponseDto::toDto).toList();
    }

    public OrderResponseDto checkedDeliveryState(Long orderId) {
        Order order = orderRepository.findOrderByIdOrElseThrow(orderId);

        return OrderResponseDto.toDto(order);
    }
}
