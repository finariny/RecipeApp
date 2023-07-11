package com.example.recipeapp.controller;

import com.example.recipeapp.service.FileService;
import com.example.recipeapp.service.IngredientService;
import com.example.recipeapp.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final FileService fileService;

    @GetMapping("/recipe/export")
    @Operation(summary = "Загрузка всех рецептов в виде json-файла")
    @ApiResponse(responseCode = "200", description = "Запрос выполнен")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = recipeService.getRecipeFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes\"")
                    .body(resource);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipe/format")
    @Operation(summary = "Загрузка всех рецептов в отформатированном виде")
    public ResponseEntity<Object> downloadFormattedRecipeFile() {
        try {
            Path path = recipeService.getFormattedRecipeFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Замена сохраненного на локальном диске json-файла с рецептами на новый")
    @ApiResponse(responseCode = "200", description = "Запрос выполнен")
    public ResponseEntity<Void> uploadRecipeFile(@RequestBody MultipartFile multipartFile) {
        fileService.cleanFile(recipeService.recipeFileUrl());
        File file = recipeService.getRecipeFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping(value = "/ingredient/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Замена сохраненного на локальном диске json-файла с ингредиентами на новый")
    @ApiResponse(responseCode = "200", description = "Запрос выполнен")
    public ResponseEntity<Void> uploadIngredientFile(@RequestBody MultipartFile multipartFile) {
        fileService.cleanFile(ingredientService.ingredientFileUrl());
        File file = ingredientService.getIngredientFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
