package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.domain.products.LegoSet;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import lombok.Setter;

public class UpdateQuantityOperation extends Operation<LegoSet> {

    @Setter
    protected int orderQuantityValue;

    public UpdateQuantityOperation() {
        this.type = OperationType.WRITE;
    }

    @Override
    public LegoSet execute() {
        parameter = new LegoSet(compensationParameter); // create a new object to not modify the one used for rollback

        if ((orderQuantityValue > 0) && (parameter.getAvailableUnits() - orderQuantityValue < 0)) {
            throw new RuntimeException("Not enough available units to process order.");
        }

        parameter.setAvailableUnits(parameter.getAvailableUnits() + orderQuantityValue);
        LegoSet result = jpaRepository.save(parameter);
        this.executed = true;
        return result;
    }

    @Override
    public void executeCompensation() {
        jpaRepository.save(compensationParameter);
    }
}
