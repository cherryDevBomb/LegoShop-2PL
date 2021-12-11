package com.ubb.legoshop.scheduler.model;

import com.ubb.legoshop.scheduler.model.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Transaction {

    private String uuid;
    private LocalDateTime timestamp;
    private TransactionStatus status;
    private List<Operation<Object, Object>> operations;
}
