package com.staccato.diet.planner.service.impl;

import com.staccato.diet.planner.recipe.demodata.RecipeSingleton;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.service.api.RecipeService;
import com.staccato.diet.planner.user.UserPreference;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
public class RecipeServiceImpl implements RecipeService {

    @Override
    public Set<Recipe> findAll() {
        return RecipeSingleton.INSTANCE.getRecipes();
    }

    @Override
    public int score(Set<Recipe> recipes, UserPreference userPreferences) {
        return 0;
    }
}
