package com.ubb.legoshop.persistence.repository;

import com.ubb.legoshop.persistence.domain.Order;
import com.ubb.legoshop.persistence.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class OrderRepository implements AbstractRepository<Order> {

    @Autowired
    @Qualifier("productsJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private OrderMapper orderMapper;

    private final static String INSERT_QUERY = "INSERT INTO orders(customer_id, legoset_id, created_date) VALUES (:customer_id, :legoset_id, :created_date)";


    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order add(Order entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_QUERY, getSqlParameterSourceForEntity(entity), keyHolder);
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public void delete(Order entity) {

    }

    private SqlParameterSource getSqlParameterSourceForEntity(Order order) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if (order != null) {
            parameterSource.addValue("customer_id", order.getCustomerId());
            parameterSource.addValue("legoset_id", order.getLegoSetId());
            parameterSource.addValue("created_date", order.getCreatedDate());
        }
        return parameterSource;
    }
}
