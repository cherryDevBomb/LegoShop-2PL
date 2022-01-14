package com.ubb.legoshop.scheduler.service;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.persistence.repository.CustomerRepository;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import com.ubb.legoshop.scheduler.model.operation.FindCustomerByEmailOperation;
import com.ubb.legoshop.scheduler.model.operation.InsertOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ServiceHelper serviceHelper;
    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        // define the operations that will be part of the transaction
        InsertOperation<Customer> insertCustomerOp = new InsertOperation<>();
        insertCustomerOp.setResourceTable(Table.CUSTOMER);
        insertCustomerOp.setRepository(customerRepository);
        insertCustomerOp.setParameter(customer);

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(insertCustomerOp);

        serviceHelper.sendTransactionToScheduler(transaction);

        return insertCustomerOp.getParameter();
    }

    public Customer loginCustomer(String email, String password) {
        // define the operations that will be part of the transaction
        FindCustomerByEmailOperation findCustomerOp = new FindCustomerByEmailOperation();
        findCustomerOp.setRepository(customerRepository);
        findCustomerOp.setEmailParam(email);

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(findCustomerOp);

        serviceHelper.sendTransactionToScheduler(transaction);

        Customer result = findCustomerOp.getResult();
        if (password.equals(result.getPassword())) {
            return result;
        }
        return null;
    }
}
