package com.ubb.legoshop.rest.controller;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.rest.model.LoginRequestModel;
import com.ubb.legoshop.scheduler.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer result = customerService.createCustomer(customer);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody LoginRequestModel loginRequestModel) {
        try {
            Customer result = customerService.loginCustomer(loginRequestModel.getEmail(), loginRequestModel.getPassword());
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
