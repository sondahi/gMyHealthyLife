package com.comert.mhl.web.controller.cache;

public interface CacheUpdater {

    void updateFoodIdAndNames ( CacheEvent event );
    void updateFoodCategoryIdAndNames ( CacheEvent event );
    void updateMealIdAndNames ( CacheEvent event );
    void updateMealCategoryIdAndNames ( CacheEvent event );

}
