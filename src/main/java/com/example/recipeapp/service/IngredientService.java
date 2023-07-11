package com.example.recipeapp.service;

import com.example.recipeapp.model.Ingredient;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

/**
 * Сервис для работы с ингредиентами
 */
public interface IngredientService {

    /**
     * Сохраняет ингредиент
     *
     * @param ingredient объект {@link Ingredient}
     * @return сохраненный ингредиент
     */
    Ingredient addIngredient(Ingredient ingredient);

    /**
     * Возвращает ингредиент по его идентификатору
     *
     * @param id идентификатор ингредиента
     * @return возвращаемый ингредиент
     */
    Ingredient getIngredient(long id);

    /**
     * Обновляет ингредиент
     *
     * @param id         идентификатор ингредиента
     * @param ingredient объект {@link Ingredient}
     * @return обновленный ингредиент
     */
    Ingredient updateIngredient(long id, Ingredient ingredient);

    /**
     * Удаляет ингредиент по его идентификатору
     *
     * @param id идентификатор ингредиента
     * @return <code>true</code> если ингредиент удален, <code>false</code> если ингредиент не удален
     */
    boolean deleteIngredient(long id);

    /**
     * Возвращает карту со всеми ингредиентами
     *
     * @return карта со всеми ингредиентами
     */
    Map<Long, Ingredient> getAllIngredients();

    /**
     * Возвращает метаинформацию о файле ингредиентов
     *
     * @return метаинформация о файле ингредиентов
     */
    File getIngredientFile();

    /**
     * Возвращает путь до файла ингредиентов
     *
     * @return путь до файла ингредиентов
     */
    Path ingredientFileUrl();
}
