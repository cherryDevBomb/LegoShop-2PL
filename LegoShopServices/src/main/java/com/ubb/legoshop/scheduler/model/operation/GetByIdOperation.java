package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.scheduler.model.enums.OperationType;

public class GetByIdOperation<T> extends Operation<T> {

    public GetByIdOperation() {
        this.type = OperationType.READ;
    }

    @Override
    public void execute() {
        T getResult = repository.getById(resourceId);
        if (getResult == null) {
            throw new RuntimeException("Resource not found.");
        }
    }

    @Override
    public void executeCompensation() {
        // no compensation for an operation of type READ
    }
}
