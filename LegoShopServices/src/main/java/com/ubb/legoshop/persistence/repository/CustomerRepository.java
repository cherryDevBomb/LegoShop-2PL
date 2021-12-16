package com.ubb.legoshop.persistence.repository;

import com.ubb.legoshop.persistence.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository implements AbstractRepository<Customer> {

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public Customer add(Customer entity) {
        return null;
    }

    @Override
    public void delete(Customer entity) {

    }
}
