package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Setter;

public class FindLegoSetAndCheckStockOperation extends Operation<LegoSet> {

    @Setter
    private int orderQuantity;

    public FindLegoSetAndCheckStockOperation() {
        this.type = OperationType.READ;
        this.resourceTable = Table.LEGO_SET;
    }

    @Override
    public void execute() {
        LegoSet getResult = repository.getById(resourceId);

        if (getResult == null) {
            throw new RuntimeException("Resource not found.");
        }

        // check if the desired quantity is available
        // orderQuantity will be negative when an order is canceled
        if ((orderQuantity > 0) && (getResult.getAvailableUnits() - orderQuantity) < 0) {
            throw new RuntimeException("Not enough available units to process order.");
        }
    }

    @Override
    public void executeCompensation() {
        // no compensation for an operation of type READ
    }
}
