package com.ubb.legoshop.persistence.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private Long customerId;
    private Long legoSetId;
    private LocalDateTime createdDate;
}
