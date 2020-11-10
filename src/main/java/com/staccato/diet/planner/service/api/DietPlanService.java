package com.staccato.diet.planner.service.api;

import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.user.UserPreference;

import java.util.List;
import java.util.Set;

public interface DietPlanService {

    List<Set<Recipe>> buildPlan(UserPreference userPreference);
}
