package com.ubb.legoshop.rest.controller;

import com.ubb.legoshop.persistence.domain.customers.Customer;
import com.ubb.legoshop.scheduler.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public void createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
    }
}
