package com.ubb.legoshop;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.persistence.domain.Order;
import com.ubb.legoshop.persistence.repository.CustomerRepository;
import com.ubb.legoshop.persistence.repository.LegoSetRepository;
import com.ubb.legoshop.persistence.repository.OrderRepository;
import com.ubb.legoshop.scheduler.TransactionManager;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import com.ubb.legoshop.scheduler.model.operation.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeadlockTest {

    @Spy
    private TransactionManager transactionManager;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LegoSetRepository legoSetRepository;

    @Test
    public void testDeadlockDetection() {
        // define the operations that will be part of the transaction
        Customer customerStub = new Customer();
        customerStub.setCustomerId(1L);

        LegoSet legoSetStub = new LegoSet();
        legoSetStub.setId(2L);
        legoSetStub.setAvailableUnits(1);

        Order order1 = new Order();
        order1.setId(100L);
        order1.setCustomerId(1L);
        order1.setLegoSetId(2L);

        Order order2 = new Order();
        order2.setId(200L);
        order2.setCustomerId(1L);
        order2.setLegoSetId(2L);

        // create transaction for adding a order for legoSet with id=2
        GetByIdOperation<Customer> getCustomerByIdOp = new GetByIdOperation<>();
        getCustomerByIdOp.setResourceTable(Table.CUSTOMER);
        getCustomerByIdOp.setRepository(customerRepository);
        getCustomerByIdOp.setResourceId(customerStub.getCustomerId());

        FindLegoSetAndCheckStockOperation findLegoSetAndCheckStockOp = new FindLegoSetAndCheckStockOperation();
        findLegoSetAndCheckStockOp.setRepository(legoSetRepository);
        findLegoSetAndCheckStockOp.setResourceId(legoSetStub.getId());
        findLegoSetAndCheckStockOp.setOrderQuantity(1);

        InsertOperation<Order> insertOrderOp = new InsertOperation<>();
        insertOrderOp.setResourceTable(Table.ORDERS);
        insertOrderOp.setRepository(orderRepository);
        insertOrderOp.setParameter(order1);

        UpdateQuantityOperation updateAvailableQuantityOp = new UpdateQuantityOperation();
        updateAvailableQuantityOp.setRepository(legoSetRepository);
        updateAvailableQuantityOp.setResourceId(order1.getLegoSetId());
        updateAvailableQuantityOp.setOrderQuantity(1);

        Mockito.when(customerRepository.getById(1L)).thenReturn(customerStub);
        Mockito.when(legoSetRepository.getById(2L)).thenReturn(legoSetStub);

        Transaction transaction1 = new Transaction()
                .addOperation(getCustomerByIdOp)
                .addOperation(findLegoSetAndCheckStockOp)
                .addOperation(insertOrderOp)
                .addOperation(updateAvailableQuantityOp);

        // create transaction for deleting a order for legoSet with id=2
        GetByIdOperation<Order> getOrderByIdOp = new GetByIdOperation<>();
        getOrderByIdOp.setResourceTable(Table.ORDERS);
        getOrderByIdOp.setRepository(orderRepository);
        getOrderByIdOp.setResourceId(order2.getId());

        DeleteOperation<Order> deleteOrderOp = new DeleteOperation<>();
        deleteOrderOp.setResourceTable(Table.ORDERS);
        deleteOrderOp.setRepository(orderRepository);
        deleteOrderOp.setParameter(order2);

        UpdateQuantityOperation updateAvailableQuantityOp2 = new UpdateQuantityOperation();
        updateAvailableQuantityOp2.setRepository(legoSetRepository);
        updateAvailableQuantityOp2.setResourceId(order2.getLegoSetId());
        updateAvailableQuantityOp2.setOrderQuantity(-1);

        Mockito.when(orderRepository.getById(200L)).thenReturn(order2);

        Transaction transaction2 = new Transaction()
                .addOperation(getOrderByIdOp)
                .addOperation(deleteOrderOp)
                .addOperation(updateAvailableQuantityOp2);

        // send both transactions to the scheduler in separate threads
        Thread t1 = new Thread(() -> transactionManager.executeTransaction(transaction1));
        Thread t2 = new Thread(() -> transactionManager.executeTransaction(transaction2));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // verify that one transaction was rolled back after deadlock detection
        Mockito.verify(transactionManager).rollbackTransaction(Mockito.any(), Mockito.anyInt());
        Mockito.verify(transactionManager).releaseLocks(transaction1);
        Mockito.verify(transactionManager).releaseLocks(transaction2);
    }
}
