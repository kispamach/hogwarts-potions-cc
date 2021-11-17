package com.codecool.hogwarts_potions.controller;

import com.codecool.hogwarts_potions.model.Potion;
import com.codecool.hogwarts_potions.model.Student;
import com.codecool.hogwarts_potions.service.PotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/potions")
public class PotionController {

    private PotionService potionService;

    public PotionController(PotionService potionService) {
        this.potionService = potionService;
    }

    @GetMapping
    public List<Potion> getAllPotion() {
        return potionService.getAllPotions();
    }

    @GetMapping("/{student-id}")
    public List<Potion> getPotionsByStudent(@PathVariable ("student-id") Long studentId) {
        return potionService.getPotionsByStudent(studentId);
    }

    @PostMapping
    public Potion newPotion(@RequestBody Potion potion) {
        return potionService.newPotion(potion);
    }
}
