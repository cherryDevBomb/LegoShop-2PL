package com.ubb.legoshop.scheduler.model.operation;

import com.ubb.legoshop.persistence.domain.Customer;
import com.ubb.legoshop.persistence.repository.CustomerRepository;
import com.ubb.legoshop.scheduler.model.enums.OperationType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Getter;
import lombok.Setter;

public class FindCustomerByEmailOperation extends Operation<Customer> {

    @Setter
    private String emailParam;

    @Getter
    private Customer result;

    public FindCustomerByEmailOperation() {
        this.type = OperationType.READ;
        this.resourceTable = Table.CUSTOMER;
    }

    @Override
    public void execute() {
        result = ((CustomerRepository)repository).getByEmail(emailParam);
    }

    @Override
    public void executeCompensation() {
        // no compensation for an operation of type READ
    }
}
