package com.comert.mhl.database.food.service.impl;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.food.repository.FoodRepository;
import com.comert.mhl.database.food.service.FoodService;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

import java.util.List;

@Local(value = FoodService.class)
@Stateless(name = "FoodServiceEJB")
@TransactionAttribute(value = TransactionAttributeType.NEVER)
public class FoodServiceImpl implements FoodService {

    @EJB
    private FoodRepository foodRepository;

    @Inject
    private Validator validator;

    public FoodServiceImpl() {
    }

    public FoodServiceImpl(Validator validator) {
        this.validator = validator;
    }


    @Override
    public Food findFoodById(Integer foodId) {
        return foodRepository.findFoodById(foodId);
    }

    @Override
    public void saveFood(Food food) {
        foodRepository.saveFood(food);
    }

    @Override
    public Food updateFood(Food food) {
        return foodRepository.updateFood(food);
    }

    @Override
    public void removeFood(Integer foodId) {
        foodRepository.removeFood(foodId);
    }

    @Override
    public List<Food> listFoods() {
        return foodRepository.listFoods();
    }

    @Override
    public List<Food> listFoods(int firstResult, int maxResult) {
        return foodRepository.listFoods(firstResult, maxResult);
    }

    @Override
    public List<IdAndName> listFoodsByIdAndName() {
        return foodRepository.listFoodsByIdAndName();
    }
}
