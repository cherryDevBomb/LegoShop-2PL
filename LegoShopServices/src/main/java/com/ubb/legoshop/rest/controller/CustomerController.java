package com.ubb.legoshop.rest.controller;

import com.ubb.legoshop.persistence.repository.customers.CustomerRepository;
import com.ubb.legoshop.rest.model.CustomerRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public void createCustomer(@RequestBody CustomerRequestModel customerRequestModel) {

    }
}
