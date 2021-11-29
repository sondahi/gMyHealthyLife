package com.comert.mhl.database.food.service;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.model.entity.Food;

import java.util.Set;

public interface FoodService {

    Food findFoodById(final Integer foodId);

    void saveFood(final Food food);

    Food updateFood(final Food food);

    void removeFood(final Integer foodId);

    Set<Food> listFoods();

    Set<Food> listFoods(final int firstResult, final int maxResult);

    Set<IdAndName> listFoodsByIdAndName();

}
