package com.ubb.legoshop.persistence.domain.products;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "legoset")
@Data
@NoArgsConstructor
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

    @Column(name = "available_units", nullable = false)
    private int availableUnits;

    @Column(name = "price", nullable = false)
    private double price;

    public LegoSet(LegoSet other) {
        this.id = other.id;
        this.uniqueSetId = other.uniqueSetId;
        this.setName = other.setName;
        this.category = other.category;
        this.piecesCount = other.piecesCount;
        this.availableUnits = other.availableUnits;
        this.price = other.price;
    }
}
