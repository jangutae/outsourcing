package com.example.outsourcing.order.repository;

import com.example.outsourcing.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public interface OrderRepository extends JpaRepository<Order, Long> {
  default Order findOrderByIdOrElseThrow(Long orderId) {
      return findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

}
