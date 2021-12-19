package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.domain.Order;
import com.ubb.legoshop.persistence.repository.OrderRepository;
import com.ubb.legoshop.rest.model.OrderResponseModel;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GetOrdersForCustomerOperation extends Operation<Order> {

    @Setter
    private Long customerIdParam;

    @Getter
    private List<OrderResponseModel> result;

    public GetOrdersForCustomerOperation() {
        this.type = OperationType.READ;
        this.resourceTable = Table.ORDERS;
    }

    @Override
    public void execute() {
        result = ((OrderRepository)repository).getAllByCustomerId(customerIdParam);
    }

    @Override
    public void executeCompensation() {
        // no compensation for an operation of type READ
    }
}
