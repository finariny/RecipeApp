package com.example.recipeapp.service;

import com.example.recipeapp.model.Ingredient;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long id);

    Ingredient editIngredient(long id, Ingredient ingredient);

    boolean deleteIngredient(long id);

    Map<Long, Ingredient> getAllIngredients();

    File getIngredientFile();

    Path ingredientFileUrl();
}
