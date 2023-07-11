package com.example.recipeapp.service;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;

/**
 * Сервис валидации
 */
public interface ValidationService {

    /**
     * Валидирует рецепт
     *
     * @param recipe объект {@link Recipe}
     * @return <code>true</code> если рецепт корректный, <code>false</code> если рецепт не корректный
     */
    boolean validate(Recipe recipe);

    /**
     * Валидирует ингредиент
     *
     * @param ingredient объект {@link Ingredient}
     * @return <code>true</code> если ингредиент корректный, <code>false</code> если ингредиент не корректный
     */
    boolean validate(Ingredient ingredient);
}
