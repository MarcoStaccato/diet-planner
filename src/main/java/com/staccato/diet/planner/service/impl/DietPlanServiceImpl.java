package com.staccato.diet.planner.service.impl;

import com.staccato.diet.planner.recipe.RecipePlan;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.service.PlanBuilder;
import com.staccato.diet.planner.service.api.DietPlanService;
import com.staccato.diet.planner.service.api.RecipeService;
import com.staccato.diet.planner.user.UserPreference;

import java.util.*;

public class DietPlanServiceImpl implements DietPlanService {

    RecipeService recipeService = new RecipeServiceImpl();


    @Override
    public List<Set<Recipe>> buildPlan(UserPreference userPreference) {
        Set<Recipe> recipes = recipeService.findAll();

        PlanBuilder planBuilder = new PlanBuilder(recipes, userPreference);
        List<RecipePlan> planList = planBuilder.getSuggestions();

        for(RecipePlan plan : planList) {
            int score = recipeService.score(plan.getRecipes(), userPreference);
            plan.setScore(score);
        }

        Collections.sort(planList, Comparator.comparingInt(RecipePlan::getScore));
        Collections.reverse(planList);
        List<Set<Recipe>> result = new LinkedList<>();

        int score = recipeService.score(planList.get(0).getRecipes(), userPreference);

        if(userPreference.getNumDays() > planList.size()) {
            throw new IllegalStateException("No plans found");
        }

        for (int i = 0; i < userPreference.getNumDays(); i++) {
            result.add(planList.get(i).getRecipes());
        }

        return result;
    }
}
