package com.example.recipeapp.model;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Ingredient {
    private String name;
    private int count;
    private String unit;

    public Ingredient(String name, int count, String unit) {
        setName(name);
        setCount(count);
        setUnit(unit);
    }

    public void setName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Ингредиент должен иметь название!");
        }
        this.name = name;
    }

    public void setCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Количество ингредиента не может быть меньше или 0!");
        }
        this.count = count;
    }

    public void setUnit(String unit) {
        if (StringUtils.isBlank(unit)) {
            throw new IllegalArgumentException("Ингредиент должен иметь единицу измерения!");
        }
        this.unit = unit;
    }
}
