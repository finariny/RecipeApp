package com.example.recipeapp.service.impl;

import com.example.recipeapp.exception.ValidationException;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.service.FileService;
import com.example.recipeapp.service.IngredientService;
import com.example.recipeapp.service.RecipeService;
import com.example.recipeapp.service.ValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    @Value("${path.to.recipe.file}")
    private String recipeFilePath;
    @Value("${name.of.recipe.file}")
    private String recipeFileName;

    @Value("${path.to.formatted.recipe.file}")
    private String formattedRecipeFilePath;
    @Value("${name.of.formatted.recipe.file}")
    private String formattedRecipeFileName;

    private static Map<Long, Recipe> recipeMap = new HashMap<>();
    private static long id = 1;

    private final IngredientService ingredientService;
    private final FileService fileService;
    private final ValidationService validationService;

    @PostConstruct
    private void init() {
        if (Files.exists(recipeFileUrl())) {
            readFromRecipeFile();
        }
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        if (!recipeMap.containsValue(recipe)) {
            recipeMap.put(id, recipe);
            addIngredientFromRecipe(recipe);
            saveToRecipeFile();
            return recipe;
        }
        return null;
    }

    @Override
    public Recipe getRecipe(long id) {
        if (recipeMap.containsKey(id)) {
            return recipeMap.get(id);
        }
        return null;
    }

    @Override
    public Recipe updateRecipe(long id, Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        if (recipeMap.containsKey(id)) {
            addIngredientFromRecipe(recipe);
            recipeMap.replace(id, recipe);
            saveToRecipeFile();
            return recipeMap.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            saveToRecipeFile();
            return true;
        }
        return false;
    }

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        return recipeMap;
    }

    @Override
    public File getRecipeFile() {
        return new File(recipeFilePath + "/" + recipeFileName);
    }

    @Override
    public Path recipeFileUrl() {
        return Path.of(recipeFilePath, recipeFileName);
    }

    @Override
    public Path getFormattedRecipeFile() throws IOException {
        Path path = Path.of(formattedRecipeFilePath, formattedRecipeFileName);
        fileService.cleanFile(path);
        for (Map.Entry<Long, Recipe> recipeEntry : recipeMap.entrySet()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(String.valueOf(recipeEntry.getKey()))
                        .append(". ")
                        .append(recipeEntry.getValue().getName())
                        .append("\n")
                        .append("   ")
                        .append("Время приготовления: ")
                        .append(String.valueOf(recipeEntry.getValue().getCookingTime()))
                        .append(" мин.")
                        .append("\n")
                        .append("   ")
                        .append("Ингредиенты:")
                        .append("\n");
                String bullet = "•";
                for (Ingredient ingredient : recipeEntry.getValue().getIngredients()) {
                    writer.append("    ")
                            .append(bullet)
                            .append(" ")
                            .append(ingredient.getName())
                            .append(" — ")
                            .append(String.valueOf(ingredient.getCount()))
                            .append(" ")
                            .append(ingredient.getUnit())
                            .append("\n");
                }
                writer.append("   ")
                        .append("Инструкция приготовления:")
                        .append("\n");
                int cookingStepNumber = 1;
                for (String cookingStep : recipeEntry.getValue().getCookingSteps()) {
                    writer.append("    ")
                            .append(String.valueOf(cookingStepNumber))
                            .append(". ")
                            .append(cookingStep)
                            .append("\n");
                    cookingStepNumber++;
                }
            }
        }
        return path;
    }

    private void addIngredientFromRecipe(Recipe recipe) {
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientService.addIngredient(ingredient);
        }
    }

    private void saveToRecipeFile() {
        try {
            RecipeFile recipeFile = new RecipeFile(id++, recipeMap);
            String json = new ObjectMapper().writeValueAsString(recipeFile);
            fileService.saveToFile(recipeFileUrl(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromRecipeFile() {
        try {
            String json = fileService.readFromFile(recipeFileUrl());
            RecipeFile recipeFile = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
            id = recipeFile.getLastId() + 1;
            recipeMap = recipeFile.getRecipes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class RecipeFile {
        private long lastId = 1;
        private Map<Long, Recipe> recipes;
    }
}
