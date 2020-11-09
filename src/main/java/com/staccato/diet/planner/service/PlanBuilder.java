package com.staccato.diet.planner.service;

import com.staccato.diet.planner.suggestions.SuggestionPlanner;
import com.staccato.diet.planner.recipe.RecipePlan;
import com.staccato.diet.planner.recipe.filter.RecipeFilter;
import com.staccato.diet.planner.recipe.model.MealEnum;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.user.UserPreference;

import java.util.List;
import java.util.Set;

public class PlanBuilder {

    List<Recipe> recipeBreakfast;
    List<Recipe> recipeLunch;
    List<Recipe> recipeDinner;

    UserPreference userPreference;


    public PlanBuilder(Set<Recipe> recipes, UserPreference userPreference) {
        recipeBreakfast = RecipeFilter.filterRecipe(recipes, userPreference, MealEnum.BREAKFAST);
        recipeLunch = RecipeFilter.filterRecipe(recipes, userPreference, MealEnum.LUNCH);
        recipeDinner = RecipeFilter.filterRecipe(recipes, userPreference, MealEnum.DINNER);

        this.userPreference = userPreference;
    }

    public List<RecipePlan> getSuggestions() {
        SuggestionPlanner planner = new SuggestionPlanner(recipeBreakfast, recipeLunch, recipeDinner);
        return planner.buildSuggestions(userPreference);
    }


}
