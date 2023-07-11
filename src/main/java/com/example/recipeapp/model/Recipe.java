package com.example.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private LinkedList<Ingredient> ingredients;
    private LinkedList<String> cookingSteps;
}
