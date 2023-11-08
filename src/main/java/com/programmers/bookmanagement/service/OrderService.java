package com.programmers.bookmanagement.service;

import com.programmers.bookmanagement.model.Email;
import com.programmers.bookmanagement.model.Order;
import com.programmers.bookmanagement.model.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    Order createOrder(String name, Email email, String address, String postcode, List<OrderItem> orderItems);

    List<Order> findAll();

    Order deleteById(UUID orderId);

    Optional<Order> findById(UUID orderId);
}
