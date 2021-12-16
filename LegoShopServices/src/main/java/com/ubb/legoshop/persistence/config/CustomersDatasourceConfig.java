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
public class CustomersDatasourceConfig {

    @Bean
    @ConfigurationProperties("app.datasource.customers")
    public DataSourceProperties customersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.customers.configuration")
    public DataSource customersDataSource(@Qualifier("customersDataSourceProperties") DataSourceProperties customersDataSourceProperties) {
        return customersDataSourceProperties.initializeDataSourceBuilder().type(DriverManagerDataSource.class).build();
    }

    @Bean(name = "customersJdbcTemplate")
    public NamedParameterJdbcTemplate customersJdbcTemplate(@Qualifier("customersDataSource") DataSource customersDataSource) {
        return new NamedParameterJdbcTemplate(customersDataSource);
    }
}
