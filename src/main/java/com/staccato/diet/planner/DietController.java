package com.staccato.diet.planner;

import com.staccato.diet.planner.recipe.model.Recipe;
import com.staccato.diet.planner.service.api.DietPlanService;
import com.staccato.diet.planner.service.impl.DietPlanServiceImpl;
import com.staccato.diet.planner.user.UserPreference;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
public class DietController {

    DietPlanService dietPlanService = new DietPlanServiceImpl();

    @PostMapping("/diet")
    List<Set<Recipe>> buildDiet(@RequestBody UserPreference userPreference) {
        return dietPlanService.buildPlan(userPreference);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
