package com.codecool.hogwarts_potions.controller;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.service.PotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/potions")
public class PotionController {

    private PotionService potionService;

    public PotionController(PotionService potionService) {
        this.potionService = potionService;
    }

    //    @Autowired
//    public void setPotionService(PotionService potionService) {
//        this.potionService = potionService;
//    }

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

    @PostMapping("/brew")
    public Potion brewingPotion(@RequestBody IdDTO studentId) {
        return potionService.brewingPotion(studentId.getId());
    }

    @PutMapping("/{potion-id}/add")
    public Potion addIngredient(@PathVariable ("potion-id") Long potionId, @RequestBody Ingredient ingredient) {
        return potionService.addIngredientToPotion(potionId, ingredient);
    }

    @GetMapping("/{potion-id}/help")
    public List<Recipe> getRecipeToHelp(@PathVariable ("potion-id") Long potionId) {
        return null; //potionService.getRecipesToHelp(potionId);
    }
}
