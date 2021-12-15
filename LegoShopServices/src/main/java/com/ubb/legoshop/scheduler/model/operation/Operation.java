package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

@Getter
@Setter
public abstract class Operation<T> {

    protected OperationType type;
    protected boolean executed; // TODO reevaluate if needed

    protected Table resourceTable;
    protected Long resourceId;
    protected JpaRepository<T, Long> jpaRepository;

    protected T parameter;
    protected T compensationParameter;

    public abstract T execute();
    public abstract void executeCompensation();
}
