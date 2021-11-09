package com.comert.mhl.database.meal.service.impl;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.meal.model.entity.Meal;
import com.comert.mhl.database.meal.repository.MealRepository;
import com.comert.mhl.database.meal.service.MealService;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.List;

@Local(MealService.class)
@Stateless(name = "MealServiceEJB")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class MealServiceImpl implements MealService {

    @EJB
    private MealRepository mealRepository;

    @Inject
    private Validator validator;

    @Override
    public Meal findMealById(Long mealId) {
return null;    }

    @Override
    public void saveMeal(Meal meal) {
        mealRepository.persistEntity(meal);
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return mealRepository.mergeEntity(meal);
    }

    @Override
    public void removeMeal(Meal meal) {
        mealRepository.removeEntity(meal);
    }

    @Override
    public List<Meal> listMeals() {
        return null;
    }

    @Override
    public List<Meal> listMeals(int firstResult, int maxResult) {
        return null;
    }

    @Override
    public List<IdAndName> listMealsByIdAndName() {
        return mealRepository.listEntitiesByIdAndName();
    }
}
