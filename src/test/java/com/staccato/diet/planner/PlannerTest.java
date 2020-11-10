package com.staccato.diet.planner;

import com.staccato.diet.planner.recipe.RecipePlan;
import com.staccato.diet.planner.recipe.demodata.RecipeSingleton;
import com.staccato.diet.planner.recipe.model.DietEnum;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.service.PlanBuilder;
import com.staccato.diet.planner.service.api.DietPlanService;
import com.staccato.diet.planner.service.impl.DietPlanServiceImpl;
import com.staccato.diet.planner.service.impl.RecipeServiceImpl;
import com.staccato.diet.planner.user.UserPreference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlannerTest {

    DietPlanService dietPlanService = new DietPlanServiceImpl();

    @Test
    public void getDemoRecipePlan() {
        List<Set<Recipe>> recipePlan = dietPlanService.buildPlan(getBasicUserPreference());
        Assertions.assertFalse(recipePlan.isEmpty());
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
