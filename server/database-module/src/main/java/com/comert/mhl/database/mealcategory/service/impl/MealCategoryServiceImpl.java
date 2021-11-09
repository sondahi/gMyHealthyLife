package com.comert.mhl.database.mealcategory.service.impl;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import com.comert.mhl.database.mealcategory.repository.MealCategoryRepository;
import com.comert.mhl.database.mealcategory.service.MealCategoryService;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.List;

@Local(MealCategoryService.class)
@Stateless(name = "MealCategoryServiceEJB")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class MealCategoryServiceImpl implements MealCategoryService {

    @EJB
    private MealCategoryRepository mealCategoryRepository;

    @Inject
    private Validator validator;

    @Override
    public MealCategory findMealCategoryById(Long mealCategoryId) {
        return null;
    }

    @Override
    public void saveMealCategory(MealCategory mealCategory) {
        mealCategoryRepository.persistEntity(mealCategory);
    }

    @Override
    public MealCategory updateMealCategory(MealCategory mealCategory) {
        return mealCategoryRepository.mergeEntity(mealCategory);
    }

    @Override
    public void removeMealCategory(MealCategory mealCategory) {
        mealCategoryRepository.removeEntity(mealCategory);
    }

    @Override
    public List<MealCategory> listMealCategories() {
return null;    }

    @Override
    public List<MealCategory> listMealCategories(int firstResult, int maxResult) {
        return null;
    }

    @Override
    public List<IdAndName> listMealCategoriesByIdAndName() {
        return mealCategoryRepository.listEntitiesByIdAndName();
    }
}
