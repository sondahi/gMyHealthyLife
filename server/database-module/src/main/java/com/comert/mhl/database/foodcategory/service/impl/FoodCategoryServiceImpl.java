package com.comert.mhl.database.foodcategory.service.impl;

import com.comert.mhl.database.common.exception.*;
import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.common.validator.ValidationUtils;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.foodcategory.repository.FoodCategoryRepository;
import com.comert.mhl.database.foodcategory.service.FoodCategoryService;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.Set;

@Local(value = FoodCategoryService.class)
@Stateless(name = "FoodCategoryServiceEJB")
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @EJB
    private FoodCategoryRepository foodCategoryRepository;

    @Inject
    private ValidationUtils validationUtils;

    public FoodCategoryServiceImpl() {
    }

    public FoodCategoryServiceImpl(FoodCategoryRepository foodCategoryRepository, ValidationUtils validationUtils) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public void saveFoodCategory(final FoodCategory foodCategory) {
        if (foodCategory.getFoodCategoryId() != null)
            throw new EntityNotSavedException("Field must be null", "foodCategoryId");

        try {
            validationUtils.validateEntityFields(foodCategory);
        } catch (EntityFieldNotValidException exception) {
            throw new EntityNotSavedException(exception.getMessage(), exception.getProperty());
        }

        foodCategoryRepository.saveFoodCategory(foodCategory);
    }

    @Override
    public FoodCategory findFoodCategoryById(final Integer foodCategoryId) {
        if (foodCategoryId == null)
            throw new EntityNotFoundException("Field can not be null", "foodCategoryId");

        Optional<FoodCategory> foodCategory = Optional.ofNullable(foodCategoryRepository.findFoodCategoryById(foodCategoryId));

        return foodCategory.orElseThrow(
                () -> new EntityNotFoundException("Food Category could not be found", foodCategoryId.toString()));
    }

    @Override
    public FoodCategory updateFoodCategory(final FoodCategory foodCategory) {
        if (foodCategory.getFoodCategoryId() == null)
            throw new EntityNotUpdatedException("Field can not be null", "foodCategoryId");

        try {
            validationUtils.validateEntityFields(foodCategory);
        } catch (EntityFieldNotValidException exception) {
            throw new EntityNotUpdatedException(exception.getMessage(), exception.getProperty());
        }

        return foodCategoryRepository.updateFoodCategory(foodCategory);
    }

    @Override
    public void deleteFoodCategory(final Integer foodCategoryId) {
        if (foodCategoryId == null)
            throw new EntityNotDeletedException("Field can not be null", "foodCategoryId");

        foodCategoryRepository.deleteFoodCategory(foodCategoryId);
    }

    @Override
    public Set<FoodCategory> listFoodCategories() throws EntityNotFoundException{
        final var foodCategoriesSet = foodCategoryRepository.listFoodCategories();

        if(foodCategoriesSet.isEmpty())
            throw new EntityNotFoundException("Food Categories could not be found","Food Categories");

        return foodCategoriesSet;
    }

    @Override
    public Set<FoodCategory> listFoodCategories(final int firstResult, final int maxResult) throws EntityNotFoundException{
        if(firstResult <0 || maxResult<0)
            throw new EntityNotFoundException("Fields can not be negative","firstResult or maxResult");

        final var foodCategoriesSet = foodCategoryRepository.listFoodCategories(firstResult, maxResult);

        if(foodCategoriesSet.isEmpty())
            throw new EntityNotFoundException("Food Categories could not be found","firstResult : "+firstResult+" , maxResult : "+maxResult);

        return foodCategoriesSet;
    }

    @Override
    public Set<IdAndName> listFoodCategoriesByIdAndName() throws EntityNotFoundException{
        final var foodCategoryIdAndNamesSet = foodCategoryRepository.listFoodCategoriesByIdAndName();

        if(foodCategoryIdAndNamesSet.isEmpty())
            throw new EntityNotFoundException("Food Category Id And Names could not be found","Food Categories");

        return foodCategoryIdAndNamesSet;
    }

}
