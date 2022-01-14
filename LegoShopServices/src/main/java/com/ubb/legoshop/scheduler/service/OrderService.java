package com.ubb.legoshop.scheduler.service;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.persistence.domain.Order;
import com.ubb.legoshop.persistence.repository.CustomerRepository;
import com.ubb.legoshop.persistence.repository.LegoSetRepository;
import com.ubb.legoshop.persistence.repository.OrderRepository;
import com.ubb.legoshop.rest.model.OrderResponseModel;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import com.ubb.legoshop.scheduler.model.operation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ServiceHelper serviceHelper;
    private final OrderRepository orderRepository;
    private final LegoSetRepository legoSetRepository;
    private final CustomerRepository customerRepository;

    public Order createOrder(Order order) {
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

        return insertOrderOp.getParameter();
    }

    public void deleteOrder(Order order) {
        // define the operations that will be part of the transaction
        GetByIdOperation<Order> getOrderByIdOp = new GetByIdOperation<>();
        getOrderByIdOp.setResourceTable(Table.ORDERS);
        getOrderByIdOp.setRepository(orderRepository);
        getOrderByIdOp.setResourceId(order.getId());

        DeleteOperation<Order> deleteOrderOp = new DeleteOperation<>();
        deleteOrderOp.setResourceTable(Table.ORDERS);
        deleteOrderOp.setRepository(orderRepository);
        deleteOrderOp.setParameter(order);

        UpdateQuantityOperation updateAvailableQuantityOp = new UpdateQuantityOperation();
        updateAvailableQuantityOp.setRepository(legoSetRepository);
        updateAvailableQuantityOp.setResourceId(order.getLegoSetId());
        updateAvailableQuantityOp.setOrderQuantity(-1);

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(getOrderByIdOp)
                .addOperation(deleteOrderOp)
                .addOperation(updateAvailableQuantityOp);

        serviceHelper.sendTransactionToScheduler(transaction);
    }

    public List<OrderResponseModel> getOrdersForCustomer(Long customerId) {
        // define the operations that will be part of the transaction
        GetOrdersForCustomerOperation getOrdersForCustomerOp = new GetOrdersForCustomerOperation();
        getOrdersForCustomerOp.setRepository(orderRepository);
        getOrdersForCustomerOp.setCustomerIdParam(customerId);

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(getOrdersForCustomerOp);

        serviceHelper.sendTransactionToScheduler(transaction);

        return getOrdersForCustomerOp.getResult();
    }
}
