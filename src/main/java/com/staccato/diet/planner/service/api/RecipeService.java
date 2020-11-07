package com.staccato.diet.planner.service.api;

import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.user.UserPreference;

import java.util.Set;

public interface RecipeService {

    // return all Recipe entities from the data store
    Set<Recipe> findAll();

    int score(Set<Recipe> recipes, UserPreference userPreferences);

}
