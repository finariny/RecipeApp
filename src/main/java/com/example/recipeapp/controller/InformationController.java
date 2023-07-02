package com.example.recipeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Информация")
@ApiResponse(responseCode = "200", description = "Запрос выполнен")
public class InformationController {

    @GetMapping
    @Operation(summary = "Вывод сообщения о запуске приложения")
    public ResponseEntity<String> launch() {
        return ResponseEntity.ok("<b>Приложение запущено</b>");
    }

    @GetMapping("/info")
    @Operation(summary = "Информация о приложении")
    public ResponseEntity<String> info() {
        String info = "<b>Название:</b> RecipeApp<br>"
                .concat("<b>Описание:</b> Приложение для сайта рецептов<br>")
                .concat("<b>Дата создания:</b> 27.06.2023<br>")
                .concat("<b>Автор:</b> Анастасия Драгомирова<br>");
        return ResponseEntity.ok(info);
    }
}
