package com.kalpavriksh.service;

import com.kalpavriksh.model.Plant;
import com.kalpavriksh.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    private final PlantRepository repo;

    public PlantService(PlantRepository repo) {
        this.repo = repo;
    }

    public Plant createPlant(Plant plant) {
        return repo.save(plant);
    }

    public Optional<Plant> getPlant(String id) {
        return repo.findById(id);
    }

    public List<Plant> getAllPlants() {
        return repo.findAll();
    }

    public Plant updatePlant(String id, Plant plant) {
        return repo.findById(id).map(existing -> {
            existing.setName(plant.getName());
            existing.setBotanicalName(plant.getBotanicalName());
            existing.setDescription(plant.getDescription());
            existing.setPrice(plant.getPrice());
            existing.setQuantity(plant.getQuantity());
            return repo.save(existing);
        }).orElse(null);
    }

    public void deletePlant(String id) {
        repo.deleteById(id);
    }
}
