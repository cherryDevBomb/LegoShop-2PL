package com.ubb.legoshop.persistence.mapper;

import com.ubb.legoshop.rest.model.OrderResponseModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OrderResponseMapper implements RowMapper<OrderResponseModel> {

    @Override
    public OrderResponseModel mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderResponseModel order = new OrderResponseModel();
        order.setId(resultSet.getLong("id"));
        order.setCustomerId(resultSet.getLong("customer_id"));
        order.setLegoSetId(resultSet.getLong("legoset_id"));
        order.setCreatedDate(resultSet.getObject("created_date", LocalDateTime.class));
        order.setUniqueSetId(resultSet.getString("unique_set_id"));
        order.setSetName(resultSet.getString("set_name"));
        order.setPrice(resultSet.getFloat("price"));
        return order;
    }
}
