package com.comert.mhl.database.mealcategory.service;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;

import java.util.List;

public interface MealCategoryService {

    MealCategory findMealCategoryById(Long mealCategoryId);

    void saveMealCategory(MealCategory mealCategory);

    MealCategory updateMealCategory(MealCategory mealCategory);

    void removeMealCategory(MealCategory mealCategory);

    List<MealCategory> listMealCategories();

    List<MealCategory> listMealCategories(int firstResult, int maxResult);

    List<IdAndName> listMealCategoriesByIdAndName();

}
