package com.ubb.legoshop.persistence.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ProductsDatasourceConfig {

    @Bean
    @ConfigurationProperties("app.datasource.products")
    public DataSourceProperties productsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.products.configuration")
    public DataSource productsDataSource(DataSourceProperties productsDataSourceProperties) {
        return productsDataSourceProperties.initializeDataSourceBuilder().type(DriverManagerDataSource.class).build();
    }

    @Bean(name = "productsJdbcTemplate")
    public NamedParameterJdbcTemplate productsJdbcTemplate(@Qualifier("productsDataSource") DataSource productsDataSource) {
        return new NamedParameterJdbcTemplate(productsDataSource);
    }
}
