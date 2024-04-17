package com.utkarsh.dietplanner.controller;

//import com.utkarsh.dietplanner.Models.Doctor;
import com.utkarsh.dietplanner.Models.Meal;
import com.utkarsh.dietplanner.service.MealService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Meals")
public class MealController {

    @Autowired
    MealService mealService;

    @GetMapping("allMeals")
    public List<Meal> getAllMeals(){
        return mealService.getAllMeals();
    }

    @GetMapping("doctor/{docId}")
    public List<Meal> getAllDocMeals(@PathVariable("docId")  Integer doctorId){
        return mealService.getMealsOfDoctor(doctorId);
    }

//    @GetMapping("client/{clientId}")
//    public List<Meal> getAllClientMeals(@PathVariable Integer clientId){
//        return mealService.getAllClientMeals(clientId);
//    }

    @PostMapping("saveMeal")
    public int saveMeal(@RequestBody Meal meal){
//        int
        return mealService.saveMeal(meal);
    }
}
