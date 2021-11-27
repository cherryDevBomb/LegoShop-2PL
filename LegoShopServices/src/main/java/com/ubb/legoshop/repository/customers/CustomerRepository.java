package com.ubb.legoshop.repository.customers;

import com.ubb.legoshop.domain.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
