package com.staccato.diet.planner.suggestions;

import com.google.common.collect.Sets;
import com.staccato.diet.planner.recipe.RecipePlan;
import com.staccato.diet.planner.recipe.filter.RecipeFilter;
import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.user.UserPreference;

import java.util.*;

/**
 * Builds cartesian products filtering by calories
 */
public class SuggestionPlanner {

    public static int BOUND = 500;

    List<Recipe> breakfastList;
    List<Recipe> lunchList;
    List<Recipe> dinnerList;

    public HashMap<UUID, Integer> calories = new HashMap<>();



    public SuggestionPlanner(List<Recipe> breakfastList,
                             List<Recipe> lunchList,
                             List<Recipe> dinnerList) {
        this.breakfastList = breakfastList;
        this.lunchList = lunchList;
        this.dinnerList = dinnerList;

        fillCaloriesMap(breakfastList);
        fillCaloriesMap(lunchList);
        fillCaloriesMap(dinnerList);
    }

    private void fillCaloriesMap(List<Recipe> recipeList) {
        if(recipeList == null) return;
        for(Recipe recipe : recipeList) {
            calories.put(recipe.getId(), recipe.getCalories()/recipe.getServings());
        }
    }

    public List<RecipePlan> buildSuggestions(UserPreference userPreference) {
        List<RecipePlan> recipePlanList = buildRecipeOptions(userPreference);
        List<RecipePlan> result = RecipeFilter.userPreferenceFilter(recipePlanList, userPreference, BOUND);
        return result;
    }

    private List<RecipePlan> buildRecipeOptions(UserPreference userPreference) {
        int maxCalories = userPreference.getDailyCaloricNeed() + BOUND;

        List<RecipePlan> result = new LinkedList<>();

        if(userPreference.getNumBreakfast() > 0) {
            int minBreakfastServings = userPreference.getNumBreakfast() * userPreference.getHouseholdSize();

            Map<Integer, List<Set<Recipe>>> breakfastMap = classifyByServingCal(
                    breakfastList,
                    userPreference.getNumBreakfast(),
                    minBreakfastServings);

            result = addToPlan(result, breakfastMap, maxCalories);
        }

        if(userPreference.getNumLunch() > 0) {
            int minLunchServings = userPreference.getNumLunch() * userPreference.getHouseholdSize();

            Map<Integer, List<Set<Recipe>>> lunchMap = classifyByServingCal(
                    lunchList,
                    userPreference.getNumLunch(),
                    minLunchServings);

            result = addToPlan(result, lunchMap, maxCalories);
        }

        if(userPreference.getNumDinner() > 0) {
            int minDinnerServings = userPreference.getNumDinner() * userPreference.getHouseholdSize();

            Map<Integer, List<Set<Recipe>>> dinnerMap = classifyByServingCal(
                    dinnerList,
                    userPreference.getNumDinner(),
                    minDinnerServings);

            result = addToPlan(result, dinnerMap, maxCalories);
        }
        return result;
    }



    private Map<Integer,  List<Set<Recipe>>> classifyByServingCal(List<Recipe> recipeList, int size, int minServings) {
        Map<Integer,  List<Set<Recipe>>> result = new HashMap<>();
        Set<Recipe> allRecipeSet = new HashSet<>(recipeList);
        Set<Set<Recipe>> recipeCombinations = Sets.combinations(allRecipeSet, size);
        for (Set<Recipe> recipeSet : recipeCombinations) {
            int servingCalories = 0;
            int servingTotal = 0;
            for(Recipe recipe : recipeSet) {
                servingCalories = servingCalories + calories.get(recipe.getId());
                servingTotal = servingTotal + recipe.getServings();
            }
            if(servingTotal < minServings) { // filter out combinations that have less servings that needed
                continue;
            }
            if(result.get(servingCalories) != null) {
                result.get(servingCalories).add(recipeSet);
            }else {
                List<Set<Recipe>> currentRecipeList = new LinkedList<>();
                currentRecipeList.add(recipeSet);
                result.put(servingCalories, currentRecipeList);
            }
        }
        return result;
    }

    private List<RecipePlan> addToPlan(List<RecipePlan> currentPlanList, Map<Integer, List<Set<Recipe>>> recipeMap, int maxCalories) {
        boolean isNewPlan = false;
        List<RecipePlan> planList = new LinkedList<>();
        if( currentPlanList.isEmpty()){
            isNewPlan = true;
        }

        List<Integer> keyList = new LinkedList<>(recipeMap.keySet());
        Collections.sort(keyList);

        //add All
        if(isNewPlan) {
            for(int i = 0; i<keyList.size(); i++) {
                int keyCurrentCalories = keyList.get(i);

                if(keyCurrentCalories > maxCalories){
                    break;
                }
                List<Set<Recipe>> recipeList = recipeMap.get(keyCurrentCalories);
                for(Set<Recipe> recipeSet : recipeList) {
                    RecipePlan plan = buildNewRecipePlan(keyCurrentCalories, recipeSet);
                    planList.add(plan);
                }
            }
        } else {
            for(RecipePlan plan : currentPlanList) {
                int currentCalories = plan.getServingCalories();
                int lowestCalories = keyList.get(0);
                //check for calories left
                if(currentCalories + lowestCalories > maxCalories) {
                    break;
                }
                for(int i = 0; i<keyList.size(); i++) {
                    int keyRecipeCalories = keyList.get(i);
                    int totalCalories = currentCalories + keyRecipeCalories;
                    if( totalCalories > maxCalories) {
                        break;
                    }
                    List<Set<Recipe>> recipeList = recipeMap.get(keyRecipeCalories);
                    for(Set<Recipe> recipeSet : recipeList) {
                        RecipePlan recipePlan = addRecipeToPlan(plan, totalCalories, recipeSet);
                        planList.add(recipePlan);
                    }
                }
            }
        }
        return planList;
    }

    private RecipePlan buildNewRecipePlan(int servingCalories, Set<Recipe> recipeSet) {
        RecipePlan result = new RecipePlan();
        result.setServingCalories(servingCalories);
        result.setRecipes(new HashSet<>(recipeSet));
        return result;
    }

    private RecipePlan addRecipeToPlan(RecipePlan plan, int servingCalories, Set<Recipe> recipeSet) {
        RecipePlan result = new RecipePlan();
        result.setRecipes(new HashSet<>(plan.getRecipes()));
        result.setServingCalories(plan.getServingCalories() + servingCalories);
        result.getRecipes().addAll(recipeSet);
        return result;
    }
}
