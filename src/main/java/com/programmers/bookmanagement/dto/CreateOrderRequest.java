package com.programmers.bookmanagement.dto;

import com.programmers.bookmanagement.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(String name, String email, String address, String postcode, List<OrderItem> orderItems) {
}
