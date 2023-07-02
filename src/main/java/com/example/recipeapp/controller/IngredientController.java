package com.example.recipeapp.controller;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты")
@ApiResponse(responseCode = "500", description = "Произошла ошибка, не зависящая от вызывающей стороны")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @Operation(summary = "Добавление ингредиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат")
    })
    public ResponseEntity<Long> addIngredient(@RequestBody Ingredient ingredient) {
        long ingredientId = ingredientService.addIngredient(ingredient);
        if (ingredientId == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ingredientId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об ингредиенте по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат")
    })
    public ResponseEntity<Ingredient> getIngredient(@PathVariable long id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиента по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат")
    })
    public ResponseEntity<Ingredient> editIngredient(@PathVariable long id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен"),
            @ApiResponse(responseCode = "400", description = "Параметры запроса отсутствуют или имеют некорректный формат")
    })
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    @Operation(summary = "Получение полного списка ингредиентов")
    @ApiResponse(responseCode = "200", description = "Запрос выполнен",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Map.class))))
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }
}
