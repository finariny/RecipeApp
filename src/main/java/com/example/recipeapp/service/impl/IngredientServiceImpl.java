package com.example.recipeapp.service.impl;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static final Map<Long, Ingredient> INGREDIENT_MAP = new HashMap<>();

    private static long id = 1;

    @Override
    public long addIngredient(Ingredient ingredient) {
        if (!(INGREDIENT_MAP.containsValue(ingredient))) {
            INGREDIENT_MAP.put(id, ingredient);
            return id++;
        }
        return 0;
    }

    @Override
    public Ingredient getIngredient(long id) {
        if (INGREDIENT_MAP.containsKey(id)) {
            return INGREDIENT_MAP.get(id);
        }
        return null;
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient ingredient) {
        if (INGREDIENT_MAP.containsKey(id)) {
            INGREDIENT_MAP.replace(id, ingredient);
            return INGREDIENT_MAP.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(long id) {
        if (INGREDIENT_MAP.containsKey(id)) {
            INGREDIENT_MAP.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        return INGREDIENT_MAP;
    }
}
