package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.scheduler.model.enums.OperationType;

public class InsertOperation<T> extends Operation<T> {

    public InsertOperation() {
        this.type = OperationType.WRITE;
    }

    @Override
    public T execute() {
        T result = repository.add(parameter);
        this.executed = true;
        this.compensationParameter = result; // result will contain the generated id which is necessary on delete
        return result;
    }

    @Override
    public void executeCompensation() {
        repository.delete(compensationParameter);
    }
}
