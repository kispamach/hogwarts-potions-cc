package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Potion;
import com.codecool.hogwarts_potions.repository.PotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotionService {

    private PotionRepository potionRepository;

    public PotionService(PotionRepository potionRepository) {
        this.potionRepository = potionRepository;
    }

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }
}
