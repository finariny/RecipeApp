package com.example.recipeapp.service;

import com.example.recipeapp.model.Ingredient;

import java.util.Map;

public interface IngredientService {
    long addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long id);

    Ingredient editIngredient(long id, Ingredient ingredient);

    boolean deleteIngredient(long id);

    Map<Long, Ingredient> getAllIngredients();
}
