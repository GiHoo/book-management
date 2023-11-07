package com.programmers.bookmanagement.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private final UUID orderId;
    private final String name;
    private final Email email;
    private final String address;
    private final String postcode;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Order(UUID orderId, String name, Email email, String address, String postcode, List<OrderItem> orderItems, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Order(UUID orderId, String name, Email email, String address, String postcode, List<OrderItem> orderItems, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
