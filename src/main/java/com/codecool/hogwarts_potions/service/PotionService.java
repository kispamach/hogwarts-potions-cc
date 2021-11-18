package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.repository.IngredientRepository;
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
    private IngredientRepository ingredientRepository;

    public PotionService(PotionRepository potionRepository,
                         RecipeRepository recipeRepository,
                         StudentRepository studentRepository,
                         IngredientRepository ingredientRepository) {
        this.potionRepository = potionRepository;
        this.recipeRepository = recipeRepository;
        this.studentRepository = studentRepository;
        this.ingredientRepository = ingredientRepository;
    }


    public Potion newPotion(Potion potion) {
        Potion newPotion = new Potion();
        BrewingStatus brewingStatus = checkBrewingStatus(potion);
        if (brewingStatus.equals(BrewingStatus.DISCOVERY)) newRecipe(potion);

        newPotion.setBrewer(potion.getBrewer());
        newPotion.setIngredients(potion.getIngredients());
        newPotion.setBrewingStatus(brewingStatus);
        newPotion.setRecipe(checkRecipeIngredients(potion.getIngredients()));
        return potionRepository.save(newPotion);
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

    public Potion brewingPotion(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Potion newPotion = new Potion();
        newPotion.setBrewer(student);
        newPotion.setBrewingStatus(BrewingStatus.BREW);
        return potionRepository.save(newPotion);
    }

    public Potion addIngredientToPotion(Long potionId, Ingredient newIngredient) {
        Potion potion = potionRepository.findById(potionId).orElse(null);
        Ingredient ingredient = saveIngredient(newIngredient);
        if (potion == null) return null;
        List<Ingredient> ingredientsListOfPotion = potion.getIngredients();
        ingredientsListOfPotion.add(ingredient);
        potion.setIngredients(ingredientsListOfPotion);
        return potion;
    }

    private Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.findIngredientByName(ingredient.getName()) == null
                ? ingredientRepository.save(ingredient)
                : ingredientRepository.findIngredientByName(ingredient.getName());
    }

    public List<Recipe> getRecipeToHelp(Long potionId) {
        Potion potion = potionRepository.findById(potionId).orElse(null);
        if (potion == null || potion.getIngredients().size() > 4) return null;
        return recipeRepository.findAllByIngredientsContains(potion.getIngredients());
    }
}
