package com.example.recipeapp.service;

import com.example.recipeapp.model.Recipe;

import java.util.Map;

public interface RecipeService {
    long addRecipe(Recipe recipe);

    Recipe getRecipe(long id);

    Recipe editRecipe(long id, Recipe recipe);

    boolean deleteRecipe(long id);

    Map<Long, Recipe> getAllRecipes();
}
