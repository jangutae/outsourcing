package com.example.outsourcing.order.repository;

import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.exception.OrderErrorCode;
import com.example.outsourcing.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findOrderByIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    List<Order> findOrderByUserId(Long userId);
}
