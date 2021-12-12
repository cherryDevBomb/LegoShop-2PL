package com.ubb.legoshop.scheduler.model;

import com.ubb.legoshop.scheduler.model.enums.TransactionStatus;
import com.ubb.legoshop.scheduler.model.operation.Operation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Transaction {

    private String uuid;
    private LocalDateTime timestamp;
    private TransactionStatus status;
    private List<Operation<?>> operations;

    public Transaction() {
        this.uuid = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.status = TransactionStatus.ACTIVE;
        operations = new ArrayList<>();
    }

    public Transaction addOperation(Operation<?> op) {
        operations.add(op);
        return this;
    }
}
