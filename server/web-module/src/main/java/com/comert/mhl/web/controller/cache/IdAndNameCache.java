package com.comert.mhl.web.controller.cache;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.service.FoodService;
import com.comert.mhl.database.foodcategory.service.FoodCategoryService;
import com.comert.mhl.database.meal.service.MealService;
import com.comert.mhl.database.mealcategory.service.MealCategoryService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.Set;

@ApplicationScoped
class IdAndNameCache implements CacheUser {

    @EJB
    private FoodService foodService;

    @EJB
    private FoodCategoryService foodCategoryService;

    @EJB
    private MealService mealService;

    @EJB
    private MealCategoryService mealCategoryService;

    private Set<IdAndName> foodIdAndNames;
    private Set<IdAndName> foodCategoryIdAndNames;
    private Set<IdAndName> mealIdAndNames;
    private Set<IdAndName> mealCategoryIdAndNames;

    @PostConstruct
    private void loadIdAndNames() {
        foodIdAndNames = foodService.listFoodsByIdAndName();
        foodCategoryIdAndNames = foodCategoryService.listFoodCategoriesByIdAndName();
        mealIdAndNames = mealService.listMealsByIdAndName();
        mealCategoryIdAndNames = mealCategoryService.listMealCategoriesByIdAndName();
    }

    @Override
    public Set<IdAndName> getFoodIdAndNames() {
        return foodIdAndNames;
    }

    @Override
    public Set<IdAndName> getFoodCategoryIdAndNames() {
        return foodCategoryIdAndNames;
    }

    @Override
    public Set<IdAndName> getMealIdAndNames() {
        return mealIdAndNames;
    }

    @Override
    public Set<IdAndName> getMealCategoryIdAndNames() {
        return mealCategoryIdAndNames;
    }


    private void updateFoodIdAndNames(@Observes @CacheUpdated(property = CacheUpdateProperty.FOOD) CacheEvent event) {
        IdAndName item = event.getItem();
        switch (event) {
            case ADD:
                foodIdAndNames.add(item);
                break;
            case REMOVE:
                foodIdAndNames.remove(item);
                break;
        }
    }

    private void updateFoodCategoryIdAndNames(@Observes @CacheUpdated(property = CacheUpdateProperty.FOOD_CATEGORY) CacheEvent event) {
        IdAndName item = event.getItem();
        switch (event) {
            case ADD:
                foodCategoryIdAndNames.add(item);
                break;
            case REMOVE:
                foodCategoryIdAndNames.remove(item);
                break;
        }
    }

    private void updateMealIdAndNames(@Observes @CacheUpdated(property = CacheUpdateProperty.MEAL) CacheEvent event) {
        IdAndName item = event.getItem();
        switch (event) {
            case ADD:
                mealIdAndNames.add(item);
                break;
            case REMOVE:
                mealIdAndNames.remove(item);
                break;
        }
    }

    private void updateMealCategoryIdAndNames(@Observes @CacheUpdated(property = CacheUpdateProperty.MEAL_CATEGORY) CacheEvent event) {
        IdAndName item = event.getItem();
        switch (event) {
            case ADD:
                mealCategoryIdAndNames.add(item);
                break;
            case REMOVE:
                mealCategoryIdAndNames.remove(item);
                break;
        }
    }

}
