package com.example.outsourcing.order.service;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.exception.*;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.repository.MenuRepository;
import com.example.outsourcing.order.dto.OrderResponseDto;
import com.example.outsourcing.order.dto.UpdateDeliveryStateRequestDto;
import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.order.enums.DeliveryState;
import com.example.outsourcing.order.repository.OrderRepository;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.store.repository.StoreRepository;
import com.example.outsourcing.user.entity.User;
import com.example.outsourcing.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public OrderResponseDto createdOrder(Long userId, Long menuId)  {
        User user = userRepository.findByIdOrElseThrows(userId);
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

        Order createdOrder = new Order(user, menu.getStore(), menu,  DeliveryState.ORDER_COMPLETE);
//
        // 사용자만 음식을 주문할 수 있습니다.
        if (!AccountRole.USER.equals(user.getRole())) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_BOSS);
        }
        // 최소주문 금액을 만족해야 합니다.
        if (menu.getPrice() < menu.getStore().getMinPrice()) {
            throw new CustomException(OrderErrorCode.MIN_PRICE_NOT_SATISFIED);
        }

        // 오픈 시간 아닙니다.
        if (LocalTime.now().isBefore(menu.getStore().getOpenTime())) {
            throw new CustomException(OrderErrorCode.OPEN_YET);
        }

        // 마감 시간이 지났습니다.
        if (LocalTime.now().isAfter(menu.getStore().getCloseTime())) {
            throw new CustomException(OrderErrorCode.ALREADY_CLOSE);
        }

        orderRepository.save(createdOrder);

        return OrderResponseDto.toDto(createdOrder);
    }

    @Transactional
    public String updatedDeliveryState(Long userId, Long menuId, Long orderId, DeliveryState state) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);
        Order order = orderRepository.findOrderByIdAndState(orderId, state);


        if (!user.getRole().equals(AccountRole.BOSS)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_USER);
        }

        if (!menu.getId().equals(menuId)) {
            throw new CustomException(OrderErrorCode.NOT_FOUND);
        }

        if (!order.getId().equals(orderId)) {
            throw new CustomException(OrderErrorCode.NOT_FOUND);
        }

        // switch  문 사용
        switch(order.getState())
        {
            case ORDER_ACCEPT :
                order.setState(DeliveryState.ORDER_ACCEPT);
                    break;
            case STILL_COOKING :
                order.setState(DeliveryState.STILL_COOKING);
                    break;
            case COOK_DONE :
                order.setState(DeliveryState.COOK_DONE);
                    break;
            case STILL_DELIVERING :
                order.setState(DeliveryState.STILL_DELIVERING);
                    break;
            case DELIVERY_COMPLETE :
                order.setState(DeliveryState.DELIVERY_COMPLETE);
                    break;
        }


        orderRepository.save(order);

        return order.getState().toString();
    }

    public List<OrderResponseDto> findAllOrderByUserId(Long userId) {
        User user = userRepository.findByIdOrElseThrows(userId);

        if (!user.getRole().equals(AccountRole.USER)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_USER);
        }

        List<Order> allOrders = orderRepository.findAll();

        return allOrders.stream().map(OrderResponseDto::toDto).toList();
    }

    public OrderResponseDto checkedDeliveryState(Long orderId) {
        Order order = orderRepository.findOrderByIdOrElseThrow(orderId);

        return OrderResponseDto.toDto(order);
    }
}
