package com.comert.mhl.database.meal.service.impl;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.meal.model.entity.Meal;
import com.comert.mhl.database.meal.repository.MealRepository;
import com.comert.mhl.database.meal.service.MealService;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.Set;

@Local(MealService.class)
@Stateless(name = "MealServiceEJB")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class MealServiceImpl implements MealService {

    @EJB
    private MealRepository mealRepository;

    @Inject
    private Validator validator;

    @Override
    public Meal findMealById(Integer mealId) {
        return mealRepository.findMealById(mealId);
    }

    @Override
    public void saveMeal(Meal meal) {
        mealRepository.saveMeal(meal);
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return mealRepository.updateMeal(meal);
    }

    @Override
    public void deleteMeal(Integer mealId) {
        mealRepository.deleteMeal(mealId);
    }

    @Override
    public Set<Meal> listMeals() {
        return mealRepository.listMeals();
    }

    @Override
    public Set<Meal> listMeals(int firstResult, int maxResult) {
        return mealRepository.listMeals(firstResult, maxResult);
    }

    @Override
    public Set<IdAndName> listMealsByIdAndName() {
        return mealRepository.listMealsByIdAndName();
    }
}
