package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.scheduler.model.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

public class DeleteOperation<T> extends Operation<T> {

    @Getter
    @Setter
    private T parameter;

    public DeleteOperation() {
        this.type = OperationType.WRITE;
    }

    @Override
    public void execute() {
        repository.delete(parameter);
    }

    @Override
    public void executeCompensation() {
        repository.add(parameter);
    }
}
