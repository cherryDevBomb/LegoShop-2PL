package com.ubb.legoshop.persistence.repository;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.persistence.mapper.CustomerMapper;
import com.ubb.legoshop.persistence.mapper.LegoSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository implements AbstractRepository<Customer> {

    @Autowired
    @Qualifier("customersJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerMapper customerMapper;

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM customer WHERE customer_id = :customer_id";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM customer WHERE email = :email";

    @Override
    public Customer getById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("customer_id", id);
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, parameterSource, customerMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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

    public Customer getByEmail(String email) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, parameterSource, customerMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
