package com.ubb.legoshop.persistence.config;

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
        basePackages = "com.ubb.legoshop.persistence.repository.products",
        entityManagerFactoryRef = "productsEntityManagerFactory",
        transactionManagerRef = "productsTransactionManager"
)
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

    @Bean
    @ConfigurationProperties("app.jpa.products")
    public JpaProperties productsJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean productsEntityManagerFactory(DataSource productsDataSource, JpaProperties productsJpaProperties) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);

        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter, productsJpaProperties.getProperties(), null);

        return builder.dataSource(productsDataSource)
                .packages("com.ubb.legoshop.persistence.domain.products")
                .persistenceUnit("products")
                .build();
    }

    @Bean
    public PlatformTransactionManager productsTransactionManager(@Qualifier("productsEntityManagerFactory") LocalContainerEntityManagerFactoryBean productsEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(productsEntityManagerFactory.getObject());
        return transactionManager;
    }
}
