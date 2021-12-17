package com.ubb.legoshop.scheduler;

import com.ubb.legoshop.scheduler.model.management.DirectedGraph;
import com.ubb.legoshop.scheduler.model.management.Lock;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import com.ubb.legoshop.scheduler.model.enums.TransactionStatus;
import com.ubb.legoshop.scheduler.model.operation.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class TransactionManager {

    private final List<Transaction> transactions = new LinkedList<>();
    private final List<Lock> locks = new LinkedList<>();
    private final DirectedGraph waitForGraph = new DirectedGraph();

    public void executeTransaction(Transaction transaction) {
        addTransaction(transaction);

        for (Operation<?> op : transaction.getOperations()) {
            boolean lockAcquired = waitAndAcquireLock(op, transaction);
            if (lockAcquired) {
                try {
                    op.execute();
                } catch (Exception e) {
                    log.error("Rollback transaction {} due to error: {}", transaction.getUuid(), e.getMessage());
                    rollbackTransaction(transaction, transaction.getOperations().indexOf(op) - 1);
                    transaction.setStatus(TransactionStatus.ERROR);
                    return;
                }
            } else {
                // deadlock was detected -> rollback all executed operations
                log.warn("Deadlock detected! Rollback transaction {}", transaction.getUuid());
                rollbackTransaction(transaction, transaction.getOperations().indexOf(op) - 1);
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
                    if (existingLock.getType().equals(op.getType()) && existingLock.getTransactionHasLockIds().contains(transaction.getUuid())) {
                        // transaction already has this lock
                        return true;
                    } else if (existingLock.getTransactionHasLockIds().contains(transaction.getUuid())) {
                        // existing lock is of another type, but belongs to the same transaction -> this will not cause deadlock
                        break;
                    } else if (existingLock.getType() == OperationType.READ && op.getType() == OperationType.READ) {
                        // 2 read locks on the same resource are allowed -> can acquire lock
                        existingLock.getTransactionHasLockIds().add(transaction.getUuid());
                        return true;
                    } else {
                        // an incompatible lock exists -> wait for lock to become available
                        waitForGraph.addNodeIfNotPresent(transaction.getUuid());
                        for (String lockHoldingTransaction : existingLock.getTransactionHasLockIds()) {
                            waitForGraph.addEdge(transaction.getUuid(), lockHoldingTransaction);
                        }
                        if (waitForGraph.isCyclic()) {
                            // return false will cause rollback for the transaction that added the last edge,
                            // causing the wait for graph to become cyclic
                            return false;
                        }

                        // if no deadlock is caused, wait until the existing lock gets released
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

    private void acquireLock(Operation<?> op, Transaction transaction) {
        Lock lock = new Lock(op.getType(), op.getResourceTable(), op.getResourceId());
        lock.getTransactionHasLockIds().add(transaction.getUuid());
        locks.add(lock);
    }

    private void rollbackTransaction(Transaction transaction, int lastExecutedOpIndex) {
        for (int i = lastExecutedOpIndex; i >= 0; i--) {
            transaction.getOperations().get(lastExecutedOpIndex).executeCompensation();
        }
        releaseLocks(transaction);
    }

    private void releaseLocks(Transaction transaction) {
        synchronized(locks) {
            for (Lock lock : locks) {
                lock.getTransactionHasLockIds().remove(transaction.getUuid());
            }
            locks.removeIf(lock -> lock.getTransactionHasLockIds().size() == 0);
            waitForGraph.removeNode(transaction.getUuid());
            locks.notify();
        }
    }

    private synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
