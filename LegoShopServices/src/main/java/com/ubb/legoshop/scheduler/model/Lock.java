package com.ubb.legoshop.scheduler.model;

import com.ubb.legoshop.scheduler.model.enums.LockType;
import com.ubb.legoshop.scheduler.model.enums.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Lock {

    private String uuid;
    private LockType type;
    private Table resourceTable;
    private Long resourceId;
    private String holderTransactionId;
}
