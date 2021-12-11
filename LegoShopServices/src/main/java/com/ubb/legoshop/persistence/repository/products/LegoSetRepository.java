package com.ubb.legoshop.persistence.repository.products;

import com.ubb.legoshop.persistence.domain.products.LegoSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegoSetRepository extends JpaRepository<LegoSet, Long> {
}
