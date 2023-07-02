package com.example.recipeapp.model;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Recipe {
    private String name;
    private int cookingTime;
    private LinkedList<Ingredient> ingredients;
    private LinkedList<String> cookingSteps;

    public Recipe(String name, int cookingTime, LinkedList<Ingredient> ingredients, LinkedList<String> cookingSteps) {
        setName(name);
        setCookingTime(cookingTime);
        setIngredients(ingredients);
        setCookingSteps(cookingSteps);
    }

    public void setName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Рецепт должен иметь название!");
        }
        this.name = name;
    }

    public void setCookingTime(int cookingTime) {
        if (cookingTime < 1) {
            throw new IllegalArgumentException("Рецепт должен иметь время приготовления!");
        }
        this.cookingTime = cookingTime;
    }

    public void setIngredients(LinkedList<Ingredient> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("Рецепт должен иметь список ингредиентов!");
        }
        this.ingredients = ingredients;
    }

    public void setCookingSteps(LinkedList<String> cookingSteps) {
        if (cookingSteps == null || cookingSteps.isEmpty()) {
            throw new IllegalArgumentException("Рецепт должен иметь шаги приготовления!");
        }
        this.cookingSteps = cookingSteps;
    }
}
