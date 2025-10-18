package com.kalpavriksh.controller;

import com.kalpavriksh.model.Plant;
import com.kalpavriksh.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService service;

    public PlantController(PlantService service) {
        this.service = service;
    }

    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return service.createPlant(plant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable String id) {
        return service.getPlant(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Plant> getAllPlants() {
        return service.getAllPlants();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable String id, @RequestBody Plant plant) {
        Plant updated = service.updatePlant(id, plant);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable String id) {
        service.deletePlant(id);
        return ResponseEntity.noContent().build();
    }
}
