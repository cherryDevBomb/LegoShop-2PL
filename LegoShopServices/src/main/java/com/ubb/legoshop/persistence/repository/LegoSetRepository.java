package com.ubb.legoshop.persistence.repository;

import com.ubb.legoshop.persistence.domain.LegoSet;
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
public class LegoSetRepository implements AbstractRepository<LegoSet> {

    @Autowired
    @Qualifier("productsJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private LegoSetMapper legoSetMapper;

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM legoset WHERE id = :id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM legoset";
    private static final String UPDATE_QUERY = "UPDATE legoset SET available_units = available_units - :order_quantity WHERE id = :id";

    @Override
    public LegoSet getById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, parameterSource, legoSetMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<LegoSet> getAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, legoSetMapper);
    }

    @Override
    public LegoSet add(LegoSet entity) {
        return null;
    }

    @Override
    public void delete(LegoSet entity) {
    }

    public void update(Long id, int orderQuantity) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("order_quantity", orderQuantity);
        jdbcTemplate.update(UPDATE_QUERY, parameterSource);
    }
}
