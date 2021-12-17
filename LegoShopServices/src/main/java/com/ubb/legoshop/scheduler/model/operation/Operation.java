package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.repository.AbstractRepository;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Operation<T> {

    protected OperationType type;

    protected Table resourceTable;
    protected Long resourceId;
    protected AbstractRepository<T> repository;

    public abstract void execute();
    public abstract void executeCompensation();
}
