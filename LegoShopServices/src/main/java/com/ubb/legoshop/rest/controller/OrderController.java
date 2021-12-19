package com.ubb.legoshop.rest.controller;

import com.ubb.legoshop.persistence.domain.Order;
import com.ubb.legoshop.rest.model.OrderResponseModel;
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

    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@RequestBody Order order) {
        try {
            orderService.deleteOrder(order);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseModel>> getAllProducts(@RequestParam Long customerId) {
        try {
            List<OrderResponseModel> result = orderService.getOrdersForCustomer(customerId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
