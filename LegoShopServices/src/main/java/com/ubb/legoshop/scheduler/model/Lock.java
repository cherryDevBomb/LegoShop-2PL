package com.ubb.legoshop.scheduler.model;

import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode
public class Lock {

    private String uuid;
    private OperationType type;
    private Table resourceTable;
    private Long resourceId;
    private List<String> transactionHasLockIds;
    private List<String> waitingTransactionIds;

    public Lock(OperationType type, Table resourceTable, Long resourceId) {
        this.type = type;
        this.resourceTable = resourceTable;
        this.resourceId = resourceId;

        this.uuid = UUID.randomUUID().toString();
        this.transactionHasLockIds = new ArrayList<>();
        this.waitingTransactionIds = new ArrayList<>();
    }
}
