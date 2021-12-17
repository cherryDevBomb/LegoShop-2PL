package com.ubb.legoshop.persistence.repository;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.persistence.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class CustomerRepository implements AbstractRepository<Customer> {

    @Autowired
    @Qualifier("customersJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerMapper customerMapper;

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM customer WHERE customer_id = :customer_id";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM customer WHERE email = :email";
    private static final String INSERT_QUERY = "INSERT INTO customer(first_name, last_name, email, password) VALUES (:first_name, :last_name, :email, :password)";
    private static final String DELETE_QUERY = "DELETE FROM customer WHERE customer_id = :customer_id";

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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_QUERY, getSqlParameterSourceForEntity(entity), keyHolder);
        entity.setCustomerId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public void delete(Customer entity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("customer_id", entity.getCustomerId());
        jdbcTemplate.update(DELETE_QUERY, parameterSource);
    }

    public Customer getByEmail(String email) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("email", email);
        try {
            return jdbcTemplate.queryForObject(FIND_BY_EMAIL_QUERY, parameterSource, customerMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private SqlParameterSource getSqlParameterSourceForEntity(Customer customer) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if (customer != null) {
            parameterSource.addValue("first_name", customer.getFirstName());
            parameterSource.addValue("last_name", customer.getLastName());
            parameterSource.addValue("email", customer.getEmail());
            parameterSource.addValue("password", customer.getPassword());
        }
        return parameterSource;
    }
}
