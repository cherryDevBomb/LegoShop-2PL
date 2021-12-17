package com.ubb.legoshop.scheduler.service;

import com.ubb.legoshop.scheduler.TransactionManager;
import com.ubb.legoshop.scheduler.model.enums.TransactionStatus;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceHelper {

    @Autowired
    private TransactionManager transactionManager;

    // send transaction to scheduler for execution
    // retry until transaction is committed, or status is error
    public void sendTransactionToScheduler(Transaction transaction) {
        while (!TransactionStatus.COMMITTED.equals(transaction.getStatus()) && !TransactionStatus.ERROR.equals(transaction.getStatus())) {
            transactionManager.executeTransaction(transaction);
        }

        if (TransactionStatus.ERROR.equals(transaction.getStatus())) {
            throw new RuntimeException("Transaction failed.");
        }
    }
}
