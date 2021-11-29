package com.comert.mhl.database.meal.service;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.meal.model.entity.Meal;

import java.util.Set;

public interface MealService {

    Meal findMealById(Integer mealId);

    void saveMeal(Meal meal);

    Meal updateMeal(Meal meal);

    void deleteMeal(Integer mealId);

    Set<Meal> listMeals();

    Set<Meal> listMeals(int firstResult, int maxResult);

    Set<IdAndName> listMealsByIdAndName();

}
