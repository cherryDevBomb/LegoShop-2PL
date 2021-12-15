package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.scheduler.model.enums.OperationType;
import lombok.Getter;

public class GetByIdOperation<T> extends Operation<T> {

    @Getter
    protected T getResult;

    public GetByIdOperation() {
        this.type = OperationType.READ;
    }

    @Override
    public T execute() {
        getResult = jpaRepository.getById(resourceId);
        this.executed = true;
        return getResult;
    }

    @Override
    public void executeCompensation() {
        // no compensation for an operation of type READ
    }
}
