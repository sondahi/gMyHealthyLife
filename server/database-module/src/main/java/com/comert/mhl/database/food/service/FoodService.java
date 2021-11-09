package com.comert.mhl.database.food.service;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;

import java.util.List;

public interface FoodService {

    Food findFoodById(final Integer foodId);

    void saveFood(final Food food);

    Food updateFood(final Food food);

    void removeFood(final Integer foodId);

    List<Food> listFoods();

    List<Food> listFoods(final int firstResult, final int maxResult);

    List<IdAndName> listFoodsByIdAndName();

}
