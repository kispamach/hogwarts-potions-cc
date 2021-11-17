package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.BrewingStatus;
import com.codecool.hogwarts_potions.model.Ingredient;
import com.codecool.hogwarts_potions.model.Potion;
import com.codecool.hogwarts_potions.model.Recipe;
import com.codecool.hogwarts_potions.repository.PotionRepository;
import com.codecool.hogwarts_potions.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotionService {

    private PotionRepository potionRepository;
    private RecipeRepository recipeRepository;

    public PotionService(PotionRepository potionRepository) {
        this.potionRepository = potionRepository;
    }

    private void checkBrewingStatus(Potion potion) {

        if (potion.getIngredients().size() < 5) {
            potion.setBrewingStatus(BrewingStatus.BREW);
        }
        else if (checkRecipeIngredients(potion.getIngredients()) != null) {
            potion.setBrewingStatus(BrewingStatus.REPLICA);
        }
        else {
            potion.setBrewingStatus(BrewingStatus.DISCOVERY);
        }
    }

    private Recipe checkRecipeIngredients(List<Ingredient> ingredients) {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes
                .stream()
                .filter(recipe -> recipe.getIngredients().equals(ingredients))
                .findFirst()
                .orElse(null);

//        return recipes
//                .stream()
//                .filter(recipe -> ingredients
//                        .stream()
//                        .allMatch(ingredient -> ingredient.getId().equals(recipe.getIngredients()
//                                .stream()
//                                .map(Ingredient::getId))))
//                .findFirst()
//                .orElse(null);

    }

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }

}
