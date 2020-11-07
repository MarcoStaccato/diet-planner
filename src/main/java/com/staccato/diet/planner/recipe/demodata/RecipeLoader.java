package com.staccato.diet.planner.recipe.demodata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.staccato.diet.planner.recipe.model.Recipe;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Loads specific recipes within resources/recipes directory
 */
@Log4j2
public class RecipeLoader {

    private static String RECIPES_BREAKFAST = "recipes/recipes-breakfast.json";
    private static String RECIPES_LUNCH = "recipes/recipes-lunch.json";
    private static String RECIPES_DINNER = "recipes/recipes-dinner.json";
    private static String END_LINE = "\n";

    public Set<Recipe> getRecipes() {
        List<String> recipesList = loadResourcesRecipes();
        Set<Recipe> recipeSet = new HashSet<>();
        for (String s : recipesList) {
            List<Recipe> recipes = parseRecipes(s);
            recipeSet.addAll(recipes);
        }
        return recipeSet;
    }

    private List<String> loadResourcesRecipes() {
        List<String> result = new ArrayList<>();
        log.debug("Loading recipes from resources directory");
        result.add(loadRecipes(RECIPES_BREAKFAST));
        result.add(loadRecipes(RECIPES_LUNCH));
        result.add(loadRecipes(RECIPES_DINNER));

        return result;
    }

    private List<Recipe> parseRecipes(String jsonRecipes) {
        Gson gson = new Gson();
        List<Recipe> recipes = new LinkedList<>();
        try {
            recipes = gson.fromJson(jsonRecipes, new TypeToken<List<Recipe>>() {}.getType());
        }catch (Exception e) {
            log.error("Unexpected parsing error at [" + jsonRecipes + "]", e);
        }
        return recipes;
    }

    private String loadRecipes(String path) {
        String result = "";

        try(InputStream is = RecipeLoader.class.getClassLoader().getResourceAsStream(path)) {
            result = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining(END_LINE));
        }catch (IOException e) {
            log.error("An exception occurred when loading resource recipes [" + path + "]", e);
        }
        return result;
    }
}
