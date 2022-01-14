package com.ubb.legoshop.rest.controller;

import com.ubb.legoshop.persistence.domain.LegoSet;
import com.ubb.legoshop.scheduler.service.LegoSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/legosets")
@RequiredArgsConstructor
public class LegoSetController {

    private final LegoSetService legoSetService;

    @GetMapping
    public ResponseEntity<List<LegoSet>> getAllProducts() {
        try {
            List<LegoSet> result = legoSetService.getAllProducts();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
