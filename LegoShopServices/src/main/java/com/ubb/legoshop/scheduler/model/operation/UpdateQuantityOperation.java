package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.persistence.repository.LegoSetRepository;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Setter;

public class UpdateQuantityOperation extends Operation<LegoSet> {

    @Setter
    private int orderQuantity;

    public UpdateQuantityOperation() {
        this.type = OperationType.WRITE;
        this.resourceTable = Table.LEGO_SET;
    }

    @Override
    public void execute() {
        ((LegoSetRepository) repository).update(resourceId, orderQuantity);
    }

    @Override
    public void executeCompensation() {
        // revert the change for available_units
        ((LegoSetRepository) repository).update(resourceId, -orderQuantity);
    }
}
