package com.ubb.legoshop.rest.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseModel {

    private Long id;
    private Long customerId;
    private Long legoSetId;
    private LocalDateTime createdDate;
    private String uniqueSetId;
    private String setName;
    private double price;
}
