package com.example.recipeapp.service.impl;

import com.example.recipeapp.exception.ValidationException;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.service.FileService;
import com.example.recipeapp.service.IngredientService;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    @Value("${path.to.ingredient.file}")
    private String ingredientFilePath;
    @Value("${name.of.ingredient.file}")
    private String ingredientFileName;

    private static Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private static long id = 1;

    private final FileService fileService;
    private final ValidationService validationService;

    @PostConstruct
    private void init() {
        if (Files.exists(ingredientFileUrl())) {
            readFromIngredientFile();
        }
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        if (!(ingredientMap.containsValue(ingredient))) {
            ingredientMap.put(id, ingredient);
            saveToIngredientFile();
            return ingredient;
        }
        return null;
    }

    @Override
    public Ingredient getIngredient(long id) {
        if (ingredientMap.containsKey(id)) {
            return ingredientMap.get(id);
        }
        return null;
    }

    @Override
    public Ingredient updateIngredient(long id, Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        if (ingredientMap.containsKey(id)) {
            ingredientMap.replace(id, ingredient);
            saveToIngredientFile();
            return ingredientMap.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(long id) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.remove(id);
            saveToIngredientFile();
            return true;
        }
        return false;
    }

    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        return ingredientMap;
    }

    @Override
    public File getIngredientFile() {
        return new File(ingredientFilePath + "/" + ingredientFileName);
    }

    @Override
    public Path ingredientFileUrl() {
        return Path.of(ingredientFilePath, ingredientFileName);
    }

    private void saveToIngredientFile() {
        try {
            IngredientFile ingredientFile = new IngredientFile(id++, ingredientMap);
            String json = new ObjectMapper().writeValueAsString(ingredientFile);
            fileService.saveToFile(ingredientFileUrl(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromIngredientFile() {
        try {
            String json = fileService.readFromFile(ingredientFileUrl());
            IngredientFile ingredientFile = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
            id = ingredientFile.getLastId() + 1;
            ingredientMap = ingredientFile.getIngredients();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IngredientFile {
        private long lastId = 1;
        private Map<Long, Ingredient> ingredients;
    }
}
