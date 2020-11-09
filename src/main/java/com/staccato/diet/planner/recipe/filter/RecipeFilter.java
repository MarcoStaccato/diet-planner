package com.staccato.diet.planner.recipe.filter;

import com.staccato.diet.planner.recipe.RecipePlan;
import com.staccato.diet.planner.recipe.model.MealEnum;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.user.UserPreference;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class RecipeFilter {

    public static List<Recipe> filterRecipe(Set<Recipe> recipeList, UserPreference userPreference, MealEnum meal) {
        List<Recipe> result = recipeList
                .stream()
                .filter(recipe -> recipe.getMeal().equals(meal))
                .filter(recipe -> recipe.getDiet().equals(userPreference.getDiet()))
                .collect(Collectors.toList());
        return result;
    }

    public static List<RecipePlan> userPreferenceFilter(List<RecipePlan> recipePlanList,
                                                    UserPreference userPreference,
                                                    final int BOUND) {

        int lowerBoundCalories = userPreference.getDailyCaloricNeed() - BOUND;
        int upperBoundCalories = userPreference.getDailyCaloricNeed() + BOUND;


        int totalRecipes = userPreference.getNumBreakfast()
                + userPreference.getNumLunch()
                + userPreference.getNumDinner();

        List<RecipePlan> result = recipePlanList.stream().filter(recipePlan ->
                recipePlan.getRecipes().size() == totalRecipes
                        && recipePlan.getServingCalories() > lowerBoundCalories
                        && recipePlan.getServingCalories() < upperBoundCalories)
                .collect(Collectors.toList());

        return result;
    }
}
