package com.ubb.legoshop.persistence.mapper;

import com.ubb.legoshop.persistence.domain.LegoSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LegoSetMapper implements RowMapper<LegoSet> {

    @Override
    public LegoSet mapRow(ResultSet resultSet, int i) throws SQLException {
        LegoSet legoSet = new LegoSet();
        legoSet.setId(resultSet.getLong("id"));
        legoSet.setUniqueSetId(resultSet.getString("unique_set_id"));
        legoSet.setSetName(resultSet.getString("set_name"));
        legoSet.setCategory(resultSet.getString("category"));
        legoSet.setPiecesCount(resultSet.getInt("pieces_count"));
        legoSet.setAvailableUnits(resultSet.getInt("available_units"));
        legoSet.setPrice(resultSet.getFloat("price"));
        return legoSet;
    }
}
