package com.ubb.legoshop.persistence.domain.products;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "legoSet")
@Data
public class LegoSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_set_id", nullable = false)
    private String uniqueSetId;

    @Column(name = "set_name", nullable = false)
    private String setName;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "pieces_count", nullable = false)
    private int piecesCount;

    @Column(name = "likes_count", nullable = false)
    private int likesCount;

    @Column(name = "available_units", nullable = false)
    private int availableUnits;

    @Column(name = "price", nullable = false)
    private double price;
}
