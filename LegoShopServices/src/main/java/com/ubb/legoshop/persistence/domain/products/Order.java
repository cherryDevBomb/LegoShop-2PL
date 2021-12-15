package com.ubb.legoshop.persistence.domain.products;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "legoset_id", nullable = false)
    private Long legoSetId;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
