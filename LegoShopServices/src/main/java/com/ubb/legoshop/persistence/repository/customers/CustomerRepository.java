package com.ubb.legoshop.persistence.repository.customers;

import com.ubb.legoshop.persistence.domain.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
