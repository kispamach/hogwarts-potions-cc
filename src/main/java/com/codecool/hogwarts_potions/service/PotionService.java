package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.repository.PotionRepository;
import com.codecool.hogwarts_potions.repository.RecipeRepository;
import com.codecool.hogwarts_potions.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotionService {

    private PotionRepository potionRepository;
    private RecipeRepository recipeRepository;
    private StudentRepository studentRepository;

    public PotionService(PotionRepository potionRepository,
                         RecipeRepository recipeRepository,
                         StudentRepository studentRepository) {
        this.potionRepository = potionRepository;
        this.recipeRepository = recipeRepository;
        this.studentRepository = studentRepository;
    }


    public Potion newPotion(Potion potion) {
        Potion newPotion = new Potion();
        BrewingStatus brewingStatus = checkBrewingStatus(potion);
        if (brewingStatus.equals(BrewingStatus.DISCOVERY)) newRecipe(potion);

        newPotion.setBrewer(potion.getBrewer());
        newPotion.setIngredients(potion.getIngredients());
        newPotion.setBrewingStatus(brewingStatus);
        newPotion.setRecipe(checkRecipeIngredients(potion.getIngredients()));
        potionRepository.save(newPotion);
        return newPotion;
    }

    private BrewingStatus checkBrewingStatus(Potion potion) {

        if (potion.getIngredients().size() < 5) {
            return BrewingStatus.BREW;
        }
        else if (checkRecipeIngredients(potion.getIngredients()) != null) {
            return BrewingStatus.REPLICA;
        }
        else {
            return BrewingStatus.DISCOVERY;
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

    private void newRecipe(Potion potion) {
        String recipeName = potion.getBrewer().getName() + "'s discovery";
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipeName);
        newRecipe.setBrewer(potion.getBrewer());
        newRecipe.setIngredients(potion.getIngredients());
        recipeRepository.save(newRecipe);
    }

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }

    public List<Potion> getPotionsByStudent(Long studentId) {
        return potionRepository.findAllByBrewer_Id(studentId);
    }
}
