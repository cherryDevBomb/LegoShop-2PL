package com.ubb.legoshop.rest.controller;

import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.persistence.domain.Order;
import com.ubb.legoshop.scheduler.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            order.setCreatedDate(LocalDateTime.now());
            Order result = orderService.createOrder(order);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllProducts(@RequestParam Long customerId) {
        try {
            List<Order> result = orderService.getOrdersForCustomer(customerId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
