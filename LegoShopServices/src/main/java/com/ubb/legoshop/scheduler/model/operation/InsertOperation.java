package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.scheduler.model.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

public class InsertOperation<T> extends Operation<T> {

    @Getter
    @Setter
    private T parameter;

    public InsertOperation() {
        this.type = OperationType.WRITE;
    }

    @Override
    public void execute() {
        T result = repository.add(parameter);
        this.parameter = result; // result will contain the generated id which is necessary on delete
    }

    @Override
    public void executeCompensation() {
        repository.delete(parameter);
    }
}
