package com.staccato.diet.planner.service.impl;

import com.staccato.diet.planner.recipe.demodata.RecipeSingleton;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.service.api.RecipeService;
import com.staccato.diet.planner.user.UserPreference;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
public class RecipeServiceImpl implements RecipeService {

    public final int HIGH_SCORE = 10000;
    public final int EXTRA_SERVINGS = 600;

    @Override
    public Set<Recipe> findAll() {
        return RecipeSingleton.INSTANCE.getRecipes();
    }

    @Override
    public int score(Set<Recipe> recipes, UserPreference userPreferences) {
        int breakfastServings = userPreferences.getHouseholdSize() * userPreferences.getNumBreakfast();
        int lunchServings = userPreferences.getHouseholdSize() * userPreferences.getNumLunch();
        int dinnerServings = userPreferences.getHouseholdSize() * userPreferences.getNumDinner();

        int totalRequestedServings = breakfastServings + lunchServings + dinnerServings;

        int recipeServings = 0;
        int servingCaloriesPerSet = 0;

        for(Recipe recipe : recipes) {
            recipeServings += recipe.getServings();
            servingCaloriesPerSet += recipe.getCalories()/recipeServings;
        }

        int extraServings = recipeServings - totalRequestedServings;

        int extraOrMissingCalories = Math.abs(userPreferences.getDailyCaloricNeed() - servingCaloriesPerSet);

        int score = HIGH_SCORE - (extraServings * EXTRA_SERVINGS);

        score = score - extraOrMissingCalories;

        return score;
    }

}
