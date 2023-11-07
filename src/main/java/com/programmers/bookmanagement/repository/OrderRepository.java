package com.programmers.bookmanagement.repository;

import com.programmers.bookmanagement.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order insert(Order order);

    List<Order> findAll();

    Order delete(UUID orderId);

    Optional<Order> findById(UUID orderId);

}
