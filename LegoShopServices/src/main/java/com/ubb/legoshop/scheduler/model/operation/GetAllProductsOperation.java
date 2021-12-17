package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.persistence.repository.LegoSetRepository;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Getter;

import java.util.List;

public class GetAllProductsOperation extends Operation<LegoSet> {

    @Getter
    private List<LegoSet> result;

    public GetAllProductsOperation() {
        this.type = OperationType.READ;
        this.resourceTable = Table.LEGO_SET;
    }

    @Override
    public void execute() {
        result = repository.getAll();
    }

    @Override
    public void executeCompensation() {
        // no compensation for an operation of type READ
    }
}
