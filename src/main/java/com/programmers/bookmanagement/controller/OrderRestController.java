package com.programmers.bookmanagement.controller;

import com.programmers.bookmanagement.dto.CreateOrderRequest;
import com.programmers.bookmanagement.model.Email;
import com.programmers.bookmanagement.model.Order;
import com.programmers.bookmanagement.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    private final OrderServiceImpl orderService;

    @GetMapping
    public List<Order> findAll(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return orders;
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(
                orderRequest.name(),
                new Email(orderRequest.email()),
                orderRequest.address(),
                orderRequest.postcode(),
                orderRequest.orderItems()
        );
    }

    @DeleteMapping("/{orderId}")
    public Order deleteBook(@PathVariable String orderId) {
        return orderService.deleteById(UUID.fromString(orderId));
    }

    @GetMapping("/{orderId}")
    public Order detailOrder(@PathVariable String orderId) {
        return orderService.findById(UUID.fromString(orderId)).orElseThrow(() -> new RuntimeException("찾지 못했습니다"));
    }
}
