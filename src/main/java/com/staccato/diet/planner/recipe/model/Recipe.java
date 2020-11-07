package com.staccato.diet.planner.recipe.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Recipe {

    UUID id;

    String name;

    MealEnum meal;

    DietEnum diet;

    int servings;

    int calories;

    List<String> ingredients;

    List<String>instructions;
}
