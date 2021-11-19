package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.*;
import com.codecool.hogwarts_potions.repository.IngredientRepository;
import com.codecool.hogwarts_potions.repository.PotionRepository;
import com.codecool.hogwarts_potions.repository.RecipeRepository;
import com.codecool.hogwarts_potions.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Potion> getAllPotions() {
        return potionRepository.findAll();
    }

    public List<Potion> getPotionsByStudent(Long studentId) {
        return potionRepository.findAllByBrewer_Id(studentId);
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


    @Transactional
    public Recipe newRecipe(Potion potion) {
        String recipeName = potion.getBrewer().getName() + "'s discovery";
        System.out.println(recipeName);
        List<Ingredient> ingredientsListOfPotion = new ArrayList<>(potion.getIngredients());
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipeName);
        newRecipe.setBrewer(potion.getBrewer());
        newRecipe.setIngredients(ingredientsListOfPotion);
        return recipeRepository.save(newRecipe);
    }


    @Transactional
    public Potion brewingPotion(Long studentId) {
        System.out.println(studentId);
        Student student = studentRepository.findById(studentId).orElse(null);
        System.out.println(student);
        Potion newPotion = new Potion();
        newPotion.setBrewer(student);
        newPotion.setBrewingStatus(BrewingStatus.BREW);
        System.out.println(newPotion);
        return potionRepository.save(newPotion);
    }

    @Transactional
    public Potion addIngredientToPotion(Long potionId, Ingredient newIngredient) {
        Potion potion = potionRepository.findById(potionId).orElse(null);
        Ingredient ingredient = saveIngredient(newIngredient);
        System.out.println(ingredient);
        if (potion == null) return null;
        List<Ingredient> ingredientsListOfPotion = new ArrayList<>(potion.getIngredients());
        ingredientsListOfPotion.add(ingredient);
        potion.setIngredients(ingredientsListOfPotion);
        BrewingStatus brewingStatus = checkBrewingStatus(potion);
        potion.setBrewingStatus(brewingStatus);
        if (brewingStatus.equals(BrewingStatus.DISCOVERY)) {
            System.out.println("alma");
            Recipe newRecipe = newRecipe(potion);
            System.out.println("settelés előtt" + newRecipe.getIngredients());
            potion.setRecipe(newRecipe);
        }
        return potion;
    }

    private Ingredient saveIngredient(Ingredient ingredient) {
        Ingredient useIngredient = ingredientRepository.findIngredientByNameIgnoreCase(ingredient.getName())
                .orElse(null);
        return useIngredient == null
                ? ingredientRepository.save(ingredient)
                : useIngredient;
    }

//    public List<Recipe> getRecipesToHelp(Long potionId) {
//        Potion potion = potionRepository.findById(potionId).orElse(null);
//        if (potion == null || potion.getIngredients().size() > 4) return null;
//        List<Ingredient> ingredientListOfPotion = new ArrayList<>(potion.getIngredients());
//        List<Recipe> recipes = new ArrayList<>(recipeRepository.findAll());
//        Set<Recipe> ingredientContainRecipe = recipes
//                .stream()
//                .map(recipe -> recipe.getIngredients()
//                        .stream()
//                        .distinct()
//                        .filter(ingredientListOfPotion::contains)
//                        .collect(Collectors.toSet()))
//                .collect(Collectors.toSet());
//        return null;
//    }
}
