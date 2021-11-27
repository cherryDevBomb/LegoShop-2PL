package com.ubb.legoshop.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.ubb.legoshop.repository.customers",
        entityManagerFactoryRef = "customersEntityManagerFactory",
        transactionManagerRef = "customersTransactionManager"
)
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


    @Bean
    @ConfigurationProperties("app.jpa.customers")
    public JpaProperties customersJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean customersEntityManagerFactory(DataSource customersDataSource, JpaProperties customersJpaProperties) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter, customersJpaProperties.getProperties(), null);

        return builder.dataSource(customersDataSource)
                .packages("com.ubb.legoshop.domain.customers")
                .persistenceUnit("customers")
                .build();
    }

    @Bean
    public PlatformTransactionManager customersTransactionManager(@Qualifier("customersEntityManagerFactory") LocalContainerEntityManagerFactoryBean customersEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(customersEntityManagerFactory.getObject());
        return transactionManager;
    }
}
