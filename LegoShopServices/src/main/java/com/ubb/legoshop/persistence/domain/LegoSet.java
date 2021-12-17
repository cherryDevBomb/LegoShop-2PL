package com.ubb.legoshop.persistence.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LegoSet {

    private Long id;
    private String uniqueSetId;
    private String setName;
    private String category;
    private int piecesCount;
    private int availableUnits;
    private double price;
}
