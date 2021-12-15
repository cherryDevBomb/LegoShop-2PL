package com.ubb.legoshop.scheduler.service;

import com.ubb.legoshop.persistence.domain.customers.Customer;
import com.ubb.legoshop.persistence.repository.customers.CustomerRepository;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import com.ubb.legoshop.scheduler.model.operation.InsertOperation;
import com.ubb.legoshop.scheduler.model.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private ServiceHelper serviceHelper;

    @Autowired
    private CustomerRepository customerRepository;

    public void createCustomer(Customer customer) {
        // define the operations that will be part of the transaction
        Operation<Customer> insertCustomerOp = new InsertOperation<>();
        insertCustomerOp.setResourceTable(Table.CUSTOMER);
        insertCustomerOp.setJpaRepository(customerRepository);
        insertCustomerOp.setParameter(customer);

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(insertCustomerOp);

        serviceHelper.sendTransactionToScheduler(transaction);
    }
}
