package com.ubb.legoshop.scheduler.service;

import com.ubb.legoshop.persistence.domain.products.LegoSet;
import com.ubb.legoshop.persistence.domain.products.Order;
import com.ubb.legoshop.persistence.repository.products.LegoSetRepository;
import com.ubb.legoshop.persistence.repository.products.OrderRepository;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.management.Transaction;
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

    public void createOrder(Order order) {
        // define the operations that will be part of the transaction
        GetByIdOperation<LegoSet> getLegoSetByIdOp = new GetByIdOperation<>();
        getLegoSetByIdOp.setResourceTable(Table.LEGO_SET);
        getLegoSetByIdOp.setJpaRepository(legoSetRepository);
        getLegoSetByIdOp.setResourceId(order.getLegoSetId());

        InsertOperation<Order> insertOrderOp = new InsertOperation<>();
        insertOrderOp.setResourceTable(Table.ORDERS);
        insertOrderOp.setJpaRepository(orderRepository);
        insertOrderOp.setParameter(order);

        UpdateQuantityOperation updateAvailableQuantityOp = new UpdateQuantityOperation();
        updateAvailableQuantityOp.setResourceTable(Table.LEGO_SET);
        updateAvailableQuantityOp.setJpaRepository(legoSetRepository);
        updateAvailableQuantityOp.setResourceId(order.getLegoSetId());
        updateAvailableQuantityOp.setOrderQuantityValue(1);                                  // order 1 unit
        updateAvailableQuantityOp.setCompensationParameter(getLegoSetByIdOp.getGetResult()); // object before the update

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(getLegoSetByIdOp)
                .addOperation(insertOrderOp)
                .addOperation(updateAvailableQuantityOp);

        serviceHelper.sendTransactionToScheduler(transaction);
    }
}
