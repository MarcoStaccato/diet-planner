package com.staccato.diet.planner;

import com.staccato.diet.planner.recipe.RecipePlan;
import com.staccato.diet.planner.recipe.demodata.RecipeSingleton;
import com.staccato.diet.planner.recipe.model.DietEnum;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.service.PlanBuilder;
import com.staccato.diet.planner.user.UserPreference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class PlannerTest {

    @Test
    public void getDemoRecipePlan() {
        Set<Recipe> recipes = RecipeSingleton.INSTANCE.getRecipes();
        UserPreference userPreference = getBasicUserPreference();
        PlanBuilder planBuilder = new PlanBuilder(recipes, userPreference);

        List<RecipePlan> test = planBuilder.getSuggestions();

        Assertions.assertFalse(test.isEmpty());
    }


    public UserPreference getBasicUserPreference() {
        UserPreference userPreference = new UserPreference();
        userPreference.setDailyCaloricNeed(2000);
        userPreference.setHouseholdSize(2);
        userPreference.setDiet(DietEnum.GENERAL_HEALTHFUL);
        userPreference.setNumDays(3);
        userPreference.setNumBreakfast(1);
        userPreference.setNumLunch(1);
        userPreference.setNumDinner(1);
        return userPreference;
    }
}
