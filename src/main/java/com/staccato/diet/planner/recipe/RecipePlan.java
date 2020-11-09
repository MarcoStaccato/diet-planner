package com.staccato.diet.planner.recipe;

import com.staccato.diet.planner.recipe.model.Recipe;
import lombok.Data;

import java.util.Set;

@Data
public class RecipePlan {

    int servingCalories;

    int score;

    Set<Recipe> recipes;
}
