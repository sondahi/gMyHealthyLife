package com.comert.mhl.database.mealcategory.service;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;

import java.util.List;
import java.util.Set;

public interface MealCategoryService {

    MealCategory findMealCategoryById(Integer mealCategoryId);

    void saveMealCategory(MealCategory mealCategory);

    MealCategory updateMealCategory(MealCategory mealCategory);

    void deleteMealCategory(final Integer mealCategoryId);

    Set<MealCategory> listMealCategories();

    Set<MealCategory> listMealCategories(int firstResult, int maxResult);

    Set<IdAndName> listMealCategoriesByIdAndName();

}
