package com.comert.mhl.database.foodcategory.service;

import com.comert.mhl.database.common.exception.EntityNotDeletedException;
import com.comert.mhl.database.common.exception.EntityNotFoundException;
import com.comert.mhl.database.common.exception.EntityNotSavedException;
import com.comert.mhl.database.common.exception.EntityNotUpdatedException;
import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;

import java.util.Set;

public interface FoodCategoryService {

    void saveFoodCategory(final FoodCategory foodCategory) throws EntityNotSavedException;

    FoodCategory findFoodCategoryById(final Integer foodCategoryId) throws EntityNotFoundException;

    FoodCategory updateFoodCategory(final FoodCategory foodCategory) throws EntityNotUpdatedException;

    void deleteFoodCategory(final Integer foodCategoryId) throws EntityNotDeletedException;

    Set<FoodCategory> listFoodCategories() throws EntityNotFoundException;

    Set<FoodCategory> listFoodCategories(final int firstResult, final int maxResult) throws EntityNotFoundException;

    Set<IdAndName> listFoodCategoriesByIdAndName() throws EntityNotFoundException;

}
