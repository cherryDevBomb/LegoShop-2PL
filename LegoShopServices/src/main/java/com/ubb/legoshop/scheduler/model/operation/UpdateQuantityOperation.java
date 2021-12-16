package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.persistence.repository.LegoSetRepository;
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

        parameter.setAvailableUnits(parameter.getAvailableUnits() - orderQuantityValue);
        ((LegoSetRepository) repository).update(parameter);
        this.executed = true;
        return parameter;
    }

    @Override
    public void executeCompensation() {
        ((LegoSetRepository) repository).update(compensationParameter);
    }
}
