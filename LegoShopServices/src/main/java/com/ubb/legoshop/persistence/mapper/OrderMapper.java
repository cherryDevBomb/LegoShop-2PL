package com.ubb.legoshop.persistence.mapper;

import com.ubb.legoshop.persistence.domain.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setCustomerId(resultSet.getLong("customer_id"));
        order.setLegoSetId(resultSet.getLong("legoset_id"));
        order.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        return order;
    }
}
