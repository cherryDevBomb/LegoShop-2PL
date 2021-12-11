package com.ubb.legoshop.scheduler;

import com.ubb.legoshop.persistence.repository.customers.CustomerRepository;
import com.ubb.legoshop.persistence.repository.products.LegoSetRepository;
import com.ubb.legoshop.scheduler.model.enums.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResourceAccessConfiguration {

    private static final Map<Table, JpaRepository<?, Long>> repositoryMap = new HashMap<>();

    public ResourceAccessConfiguration(CustomerRepository customerRepository, LegoSetRepository legoSetRepository) {
        repositoryMap.put(Table.CUSTOMER, customerRepository);
        repositoryMap.put(Table.LEGO_SET, legoSetRepository);
    }

}
