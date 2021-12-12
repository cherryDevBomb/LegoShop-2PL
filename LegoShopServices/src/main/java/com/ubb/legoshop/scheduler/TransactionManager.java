package com.ubb.legoshop.scheduler;

import com.ubb.legoshop.scheduler.model.Lock;
import com.ubb.legoshop.scheduler.model.Transaction;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.enums.TransactionStatus;
import com.ubb.legoshop.scheduler.model.operation.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
public class TransactionManager {

    private final BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();
    private final List<Lock> locks = new LinkedList<>();

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
        addTransaction(transaction); // TODO think if maybe a list is better

        for (Operation<?> op : transaction.getOperations()) {
            boolean lockAcquired = waitAndAcquireLock(op, transaction);
            if (lockAcquired) {
                op.execute();
            } else {
                // deadlock was detected -> rollback
                int currentOpIndex = transaction.getOperations().indexOf(op);
                for (int i = currentOpIndex - 1; i >= 0; i--) {
                    transaction.getOperations().get(currentOpIndex).executeCompensation();
                }
                releaseLocks(transaction);
                transaction.setStatus(TransactionStatus.ABORTED);
                return;
            }
        }

        // release locks and commit transaction
        releaseLocks(transaction);
        transaction.setStatus(TransactionStatus.COMMITTED);
    }

    // acquire lock after waiting, if necessary
    private boolean waitAndAcquireLock(Operation<?> op, Transaction transaction) {
        try {
            synchronized (locks) {
                Lock existingLock = getLockIfExists(op.getResourceTable(), op.getResourceId());
                while (existingLock != null) {
                    // 2 read locks on the same resource are allowed
                    if (existingLock.getType() == OperationType.READ && op.getType() == OperationType.READ) {
                        existingLock.getTransactionHasLockIds().add(transaction.getUuid());
                        return true;
                    } else {
                        // an incompatible lock exists

                        // TODO add to graph, check if causes cycle
                        boolean hasCycle = false;
                        if (hasCycle) {
                            // remove transaction and edge from graph
                            return false;
                        }

                        locks.wait();
                    }
                    existingLock = getLockIfExists(op.getResourceTable(), op.getResourceId());
                }
                // checking if lock exists in a loop assures that when this statement is reached no incompatible lock exists
                acquireLock(op, transaction);
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void releaseLocks(Transaction transaction) {
        synchronized(locks) {
            for (Lock lock : locks) {
                lock.getTransactionHasLockIds().remove(transaction.getUuid());
            }
            locks.removeIf(lock -> lock.getTransactionHasLockIds().size() == 0);

            //TODO remove transaction from wait-for graph vertices

            locks.notify();
        }
    }

    // search the locks list for an existing lock for the given table and record id;
    // resourceId will be null when the whole table needs to be locked, i.e. for a getAll operation
    // return null if not found
    private Lock getLockIfExists(Table resourceTable, Long resourceId) {
        return locks.stream()
                .filter(lock -> lock.getResourceTable().equals(resourceTable) &&
                        (lock.getResourceId().equals(resourceId) || lock.getResourceId() == null || resourceId == null))
                .findFirst()
                .orElse(null);
    }

    private synchronized void addTransaction(Transaction transaction) {
        transactionQueue.add(transaction);
    }

    private void acquireLock(Operation<?> op, Transaction transaction) {
        Lock lock = new Lock(op.getType(), op.getResourceTable(), op.getResourceId());
        lock.getTransactionHasLockIds().add(transaction.getUuid());
        locks.add(lock);
    }

}
