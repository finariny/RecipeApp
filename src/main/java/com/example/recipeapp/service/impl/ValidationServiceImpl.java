package com.example.recipeapp.service.impl;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.service.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validate(Recipe recipe) {
        return recipe != null
                && !StringUtils.isBlank(recipe.getName())
                && recipe.getCookingTime() > 0
                && recipe.getIngredients() != null
                && !recipe.getIngredients().isEmpty()
                && recipe.getCookingSteps() != null
                && !recipe.getCookingSteps().isEmpty();
    }

    @Override
    public boolean validate(Ingredient ingredient) {
        return ingredient != null
                && ingredient.getName() == null
                && !StringUtils.isBlank(ingredient.getName())
                && ingredient.getCount() > 0
                && ingredient.getUnit() == null
                && !StringUtils.isBlank(ingredient.getUnit());
    }
}
