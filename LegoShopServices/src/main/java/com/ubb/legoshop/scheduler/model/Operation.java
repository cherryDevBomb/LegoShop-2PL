package com.ubb.legoshop.scheduler.model;

import com.ubb.legoshop.scheduler.model.enums.OperationStatus;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Data;

import java.util.function.Function;

@Data
public class Operation<T, U> {

    private OperationStatus status;
    private OperationType type;
    private boolean executed;

    private Table resourceTable;
    private Long resourceId;

    private Function<T,U> operation;
    private Function<T,U> compensationOperation;

    public U execute(T parameter) {
        return operation.apply(parameter);
    }

    public U executeRollback(T parameter) {
        return compensationOperation.apply(parameter);
    }

    // TODO use lambdas to specify operation + compensationOp
}
