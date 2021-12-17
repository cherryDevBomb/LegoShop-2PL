package com.ubb.legoshop.scheduler.service;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.persistence.domain.Order;
import com.ubb.legoshop.persistence.repository.CustomerRepository;
import com.ubb.legoshop.persistence.repository.LegoSetRepository;
import com.ubb.legoshop.persistence.repository.OrderRepository;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import com.ubb.legoshop.scheduler.model.operation.FindLegoSetAndCheckStockOperation;
import com.ubb.legoshop.scheduler.model.operation.GetByIdOperation;
import com.ubb.legoshop.scheduler.model.operation.InsertOperation;
import com.ubb.legoshop.scheduler.model.operation.UpdateQuantityOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ServiceHelper serviceHelper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LegoSetRepository legoSetRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void createOrder(Order order) {
        // define the operations that will be part of the transaction
        GetByIdOperation<Customer> getCustomerByIdOp = new GetByIdOperation<>();
        getCustomerByIdOp.setResourceTable(Table.CUSTOMER);
        getCustomerByIdOp.setRepository(customerRepository);
        getCustomerByIdOp.setResourceId(order.getCustomerId());

        FindLegoSetAndCheckStockOperation findLegoSetAndCheckStockOp = new FindLegoSetAndCheckStockOperation();
        findLegoSetAndCheckStockOp.setRepository(legoSetRepository);
        findLegoSetAndCheckStockOp.setResourceId(order.getLegoSetId());
        findLegoSetAndCheckStockOp.setOrderQuantity(1);

        InsertOperation<Order> insertOrderOp = new InsertOperation<>();
        insertOrderOp.setResourceTable(Table.ORDERS);
        insertOrderOp.setRepository(orderRepository);
        insertOrderOp.setParameter(order);

        UpdateQuantityOperation updateAvailableQuantityOp = new UpdateQuantityOperation();
        updateAvailableQuantityOp.setRepository(legoSetRepository);
        updateAvailableQuantityOp.setResourceId(order.getLegoSetId());
        updateAvailableQuantityOp.setOrderQuantity(1);

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(getCustomerByIdOp)
                .addOperation(findLegoSetAndCheckStockOp)
                .addOperation(insertOrderOp)
                .addOperation(updateAvailableQuantityOp);

        serviceHelper.sendTransactionToScheduler(transaction);
    }
}
