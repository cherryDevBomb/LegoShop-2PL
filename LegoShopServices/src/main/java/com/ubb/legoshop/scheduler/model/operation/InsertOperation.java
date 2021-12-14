package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.scheduler.model.enums.OperationType;

public class InsertOperation<T> extends Operation<T> {

    public InsertOperation() {
        this.type = OperationType.WRITE;
    }

    @Override
    public T execute() {
        T result = jpaRepository.save(parameter);
        this.executed = true;
        this.compensationParameter = result; // result will contain the generated id which is necessary on delete
        return result;
    }

    @Override
    public T executeCompensation() {
        jpaRepository.delete(compensationParameter);
        return compensationParameter;
    }
}
