package com.staccato.diet.planner.user;

import com.staccato.diet.planner.recipe.model.DietEnum;
import lombok.Data;

@Data
public class UserPreference {

    DietEnum diet;

    int dailyCaloricNeed;

    int householdSize;

    int numDays;

    int numBreakfast;

    int numLunch;

    int numDinner;
}
