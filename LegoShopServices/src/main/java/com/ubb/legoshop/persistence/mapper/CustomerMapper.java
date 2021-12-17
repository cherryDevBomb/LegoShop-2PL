package com.ubb.legoshop.persistence.mapper;

import com.ubb.legoshop.persistence.domain.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getLong("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPassword(resultSet.getString("password"));
        return customer;
    }
}
