package com.example.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ингредиент
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    private String name;
    private int count;
    private String unit;
}
