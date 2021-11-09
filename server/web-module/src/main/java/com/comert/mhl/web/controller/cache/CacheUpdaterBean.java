package com.comert.mhl.web.controller.cache;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
class CacheUpdaterBean implements CacheUpdater{

    @Inject
    @CacheUpdated(property = CacheUpdateProperty.FOOD)
    private Event<CacheEvent> foodEvent;

    @Inject
    @CacheUpdated(property = CacheUpdateProperty.FOOD_CATEGORY)
    private Event<CacheEvent> foodCategoryEvent;

    @Inject
    @CacheUpdated(property = CacheUpdateProperty.MEAL)
    private Event<CacheEvent> mealEvent;

    @Inject
    @CacheUpdated(property = CacheUpdateProperty.MEAL_CATEGORY)
    private Event<CacheEvent> mealCategoryEvent;

    public void updateFoodIdAndNames(CacheEvent event){
        foodEvent.fire ( event );
    }
    public void updateFoodCategoryIdAndNames(CacheEvent event){
        foodCategoryEvent.fire ( event );
    }
    public void updateMealIdAndNames(CacheEvent event){
        mealEvent.fire ( event );
    }
    public void updateMealCategoryIdAndNames(CacheEvent event){
        mealCategoryEvent.fire ( event );
    }

}
