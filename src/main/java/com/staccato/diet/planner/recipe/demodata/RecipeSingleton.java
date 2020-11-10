package com.staccato.diet.planner.recipe.demodata;

import com.staccato.diet.planner.recipe.model.Recipe;

import java.util.Set;

/**
 * Holds all demo recipes on memory
 */
public enum RecipeSingleton {

    INSTANCE;

    private Set<Recipe> ALL_RECIPES;

    private RecipeSingleton() {
        ALL_RECIPES = new RecipeLoader().getRecipes();
    }

    public RecipeSingleton getInstance () {
        return INSTANCE;
    }

    public Set<Recipe> getRecipes() {
        return  ALL_RECIPES;
    }
}
