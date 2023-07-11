package com.example.recipeapp.service;

import com.example.recipeapp.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Сервис для работы с рецептами
 */
public interface RecipeService {

    /**
     * Сохраняет рецепт
     *
     * @param recipe объект {@link Recipe}
     * @return сохраненный рецепт
     */
    Recipe addRecipe(Recipe recipe);

    /**
     * Возвращает рецепт по его идентификатору
     *
     * @param id идентификатор рецепта
     * @return возвращаемый рецепт
     */
    Recipe getRecipe(long id);

    /**
     * Обновляет рецепт
     *
     * @param id     идентификатор рецепта
     * @param recipe объект {@link Recipe}
     * @return обновленный рецепт
     */
    Recipe updateRecipe(long id, Recipe recipe);

    /**
     * Удаляет рецепт по его идентификатору
     *
     * @param id идентификатор рецепта
     * @return <code>true</code> если рецепт удален, <code>false</code> если рецепт не удален
     */
    boolean deleteRecipe(long id);

    /**
     * Возвращает карту со всеми рецептами
     *
     * @return карта со всеми рецептами
     */
    Map<Long, Recipe> getAllRecipes();

    /**
     * Возвращает метаинформацию о файле рецептов
     *
     * @return метаинформация о файле рецептов
     */
    File getRecipeFile();

    /**
     * Возвращает путь до файла рецептов
     *
     * @return путь до файла рецептов
     */
    Path recipeFileUrl();

    /**
     * Возвращает путь до файла с отформатированными рецептами
     *
     * @return путь до файла с отформатированными рецептами
     * @throws IOException если возникнет ошибка ввода-вывода при открытии или создании файла
     */
    Path getFormattedRecipeFile() throws IOException;
}
