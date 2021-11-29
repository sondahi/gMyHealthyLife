package com.comert.mhl.database.mealcategory.service.impl;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import com.comert.mhl.database.mealcategory.repository.MealCategoryRepository;
import com.comert.mhl.database.mealcategory.service.MealCategoryService;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.Set;

@Local(MealCategoryService.class)
@Stateless(name = "MealCategoryServiceEJB")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class MealCategoryServiceImpl implements MealCategoryService {

    @EJB
    private MealCategoryRepository mealCategoryRepository;

    @Inject
    private Validator validator;

    @Override
    public MealCategory findMealCategoryById(Integer mealCategoryId) {
        return mealCategoryRepository.findMealCategoryById(mealCategoryId);
    }

    @Override
    public void saveMealCategory(MealCategory mealCategory) {
        mealCategoryRepository.saveMealCategory(mealCategory);
    }

    @Override
    public MealCategory updateMealCategory(MealCategory mealCategory) {
        return mealCategoryRepository.updateMealCategory(mealCategory);
    }

    @Override
    public void deleteMealCategory(final Integer mealCategoryId) {
        mealCategoryRepository.deleteMealCategory(mealCategoryId);
    }

    @Override
    public Set<MealCategory> listMealCategories() {
        return mealCategoryRepository.listMealCategories();
    }

    @Override
    public Set<MealCategory> listMealCategories(int firstResult, int maxResult) {
        return mealCategoryRepository.listMealCategories(firstResult, maxResult);
    }

    @Override
    public Set<IdAndName> listMealCategoriesByIdAndName() {
        return mealCategoryRepository.listMealCategoriesByIdAndName();
    }
}
