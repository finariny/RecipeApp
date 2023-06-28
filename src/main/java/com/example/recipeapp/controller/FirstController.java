package com.example.recipeapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping
    public String launch() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info() {
        return "<b>Название:</b> RecipeApp<br>"
                .concat("<b>Описание:</b> Приложение для сайта рецептов<br>")
                .concat("<b>Дата создания:</b> 27.06.2023<br>")
                .concat("<b>Автор:</b> Анастасия Драгомирова<br>");
    }
}
