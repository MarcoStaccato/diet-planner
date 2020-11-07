package com.staccato.diet.planner;

import com.staccato.diet.planner.recipe.demodata.RecipeLoader;
import com.staccato.diet.planner.recipe.demodata.RecipeSingleton;
import com.staccato.diet.planner.recipe.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class RecipeLoaderTest {

    RecipeLoader recipeLoader = new RecipeLoader();

    @Test
    public void recipe_loader_list () {
        Set<Recipe> recipes = recipeLoader.getRecipes();
        Assertions.assertTrue(recipes != null);
        Assertions.assertTrue(recipes.size() > 1);
    }

    @Test
    public  void recipe_loader_enum() {
        Set<Recipe> recipes = RecipeSingleton.INSTANCE.getRecipes();
        Assertions.assertTrue(recipes != null);
        Assertions.assertFalse(recipes.isEmpty());
    }
}
