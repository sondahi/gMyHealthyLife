package com.comert.mhl.database.meal.service;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.meal.model.entity.Meal;

import java.util.List;

public interface MealService {

    Meal findMealById(Integer mealId);

    void saveMeal(Meal meal);

    Meal updateMeal(Meal meal);

    void removeMeal(Meal meal);

    List<Meal> listMeals();

    List<Meal> listMeals(int firstResult, int maxResult);

    List<IdAndName> listMealsByIdAndName();

}
