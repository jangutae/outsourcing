package com.example.outsourcing.order.service;

import com.example.outsourcing.common.constants.AccountRole;
import com.example.outsourcing.common.exception.*;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.menu.repository.MenuRepository;
import com.example.outsourcing.order.dto.OrderRequestDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    // 예외 처리 아직 만들어야 합니다.  주문 생성
    @Transactional
    public OrderResponseDto createdOrder(Long userId, Long menuId, OrderRequestDto requestDto) {
        User user = userRepository.findByIdOrElseThrows(userId);
        Store store = storeRepository.findById(requestDto.storeId()).orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND));
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);
        Order createdOrder = new Order(user, store, menu, requestDto.orderPrice(), DeliveryState.ORDER_COMPLETE);

        List<Menu> allByStoreId = menuRepository.findAllByStoreId(requestDto.storeId());

        if (!allByStoreId.contains(menu)) {
            throw new CustomException(OrderErrorCode.NOT_FOUND);
        }
        // 사용자만 음식을 주문할 수 있습니다.
        if (!user.getRole().equals(AccountRole.USER)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_BOSS);
        }
        // 최소주문 금액을 만족해야 합니다.
        if (requestDto.orderPrice() < store.getMinPrice()) {
            throw new CustomException(OrderErrorCode.MIN_PRICE_NOT_SATISFIED);
        }

        // 오픈 시간 아닙니다.
        if (LocalTime.now().isBefore(createdOrder.stringToLocaltime(store.getOpenTime()))) {
            throw new CustomException(OrderErrorCode.OPEN_YET);
        }

        // 마감 시간이 지났습니다.
        if (LocalTime.now().isAfter(createdOrder.stringToLocaltime(store.getCloseTime()))) {
            throw new CustomException(OrderErrorCode.ALREADY_CLOSE);
        }

        orderRepository.save(createdOrder);

        return OrderResponseDto.toDto(createdOrder);
    }

    @Transactional
    public String updatedDeliveryState(Long userId, Long menuId, Long orderId, UpdateDeliveryStateRequestDto requestDto) {
        User user = userRepository.findByIdOrElseThrows(userId);
        // 변경 필요 에러 코드
        Store store = storeRepository.findById(requestDto.storeId()).orElseThrow(() -> new CustomException(StoreErrorCode.NOT_FOUND));
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);
        Order order = orderRepository.findOrderByIdOrElseThrow(orderId);


        if (!user.getRole().equals(AccountRole.BOSS)) {
            throw new CustomException(OrderErrorCode.NOT_ACCESS_USER);
        }

        if (!store.getId().equals(requestDto.storeId())) {
            throw new CustomException(OrderErrorCode.NOT_FOUND);
        }

        if (!menu.getId().equals(menuId)) {
            throw new CustomException(OrderErrorCode.NOT_FOUND);
        }

        if (!order.getId().equals(orderId)) {
            throw new CustomException(OrderErrorCode.NOT_FOUND);
        }

        if (requestDto.state().equals(DeliveryState.ORDER_ACCEPT)) {
            order.setState((DeliveryState.ORDER_ACCEPT));
        } else if (requestDto.state().equals(DeliveryState.STILL_COOKING)) {
            order.setState(DeliveryState.STILL_COOKING);
        } else if (requestDto.state().equals(DeliveryState.COOK_DONE)) {
            order.setState(DeliveryState.COOK_DONE);
        } else if (requestDto.state().equals(DeliveryState.STILL_DELIVERING)) {
            order.setState(DeliveryState.STILL_DELIVERING);
        } else {
            order.setState(DeliveryState.DELIVERY_COMPLETE);
        }
        orderRepository.save(order);

        return order.getState().toString();
    }

    public List<OrderResponseDto> findAllOrderByUserId(Long userId) {
        User user = userRepository.findByIdOrElseThrows(userId);

        if (!user.getRole().equals(AccountRole.USER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        List<Order> allOrders = orderRepository.findAll();

        return allOrders.stream().map(OrderResponseDto::toDto).toList();
    }
}
