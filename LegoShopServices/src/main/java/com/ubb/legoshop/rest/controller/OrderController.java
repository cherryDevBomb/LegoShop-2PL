package com.ubb.legoshop.rest.controller;

import com.ubb.legoshop.persistence.domain.products.Order;
import com.ubb.legoshop.scheduler.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody Order order) {
        order.setCreatedDate(LocalDateTime.now());
        orderService.createOrder(order);
    }
}
