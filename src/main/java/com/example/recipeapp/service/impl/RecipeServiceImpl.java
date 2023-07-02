package com.example.recipeapp.service.impl;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.service.IngredientService;
import com.example.recipeapp.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Map<Long, Recipe> RECIPE_MAP = new HashMap<>();

    private static long id = 1;

    private final IngredientService ingredientService;

    public RecipeServiceImpl(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public long addRecipe(Recipe recipe) {
        if (!RECIPE_MAP.containsValue(recipe)) {
            RECIPE_MAP.put(id, recipe);
            addIngredientFromRecipe(recipe);
            return id++;
        }
        return 0;
    }

    @Override
    public Recipe getRecipe(long id) {
        if (RECIPE_MAP.containsKey(id)) {
            return RECIPE_MAP.get(id);
        }
        return null;
    }

    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        if (RECIPE_MAP.containsKey(id)) {
            addIngredientFromRecipe(recipe);
            RECIPE_MAP.replace(id, recipe);
            return RECIPE_MAP.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (RECIPE_MAP.containsKey(id)) {
            RECIPE_MAP.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        return RECIPE_MAP;
    }

    private void addIngredientFromRecipe(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientService.addIngredient(ingredient);
        }
    }
}
