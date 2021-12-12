package com.ubb.legoshop.scheduler;

import com.ubb.legoshop.scheduler.model.operation.Operation;
import com.ubb.legoshop.scheduler.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
public class TransactionManager {

    private final BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();

//    @PostConstruct
//    public void start() {
//        Executors.newSingleThreadExecutor().execute(() -> {
//            while (true) {
//                try {
////                    Operation<?, ?> op = operationQueue.take();
////                    System.out.println(op.getResourceTable().toString());
//                    Transaction t = transactionQueue.take();
//                    for ( Operation<?> op : t.getOperations()) {
//                        System.out.println(op.getResourceTable().toString() + " " + Thread.currentThread().getName());
//                    }
//                } catch (InterruptedException e) {
//                    log.error("Error processing operation.");
//                }
//            }
//        });
//    }

    public void executeTransaction(Transaction transaction) {
        System.out.println(Thread.currentThread().getName());
    }

    synchronized public void addTransaction(Transaction transaction) {
        transactionQueue.add(transaction);
    }

}
