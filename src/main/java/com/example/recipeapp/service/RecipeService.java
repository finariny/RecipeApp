package com.example.recipeapp.service;

import com.example.recipeapp.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(long id);

    Recipe editRecipe(long id, Recipe recipe);

    boolean deleteRecipe(long id);

    Map<Long, Recipe> getAllRecipes();

    File getRecipeFile();

    Path recipeFileUrl();

    Path getFormattedRecipeFile() throws IOException;
}
