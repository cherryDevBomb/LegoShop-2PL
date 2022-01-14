package com.ubb.legoshop.scheduler.service;

import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.persistence.repository.LegoSetRepository;
import com.ubb.legoshop.scheduler.model.management.Transaction;
import com.ubb.legoshop.scheduler.model.operation.GetAllProductsOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LegoSetService {

    private final ServiceHelper serviceHelper;
    private final LegoSetRepository legoSetRepository;

    public List<LegoSet> getAllProducts() {
        // define the operations that will be part of the transaction
        GetAllProductsOperation getAllProductsOp = new GetAllProductsOperation();
        getAllProductsOp.setRepository(legoSetRepository);

        // create the transaction
        Transaction transaction = new Transaction()
                .addOperation(getAllProductsOp);

        serviceHelper.sendTransactionToScheduler(transaction);

        return getAllProductsOp.getResult();
    }
}
