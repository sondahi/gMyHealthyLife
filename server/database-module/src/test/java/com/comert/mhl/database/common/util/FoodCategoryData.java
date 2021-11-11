package com.comert.mhl.database.common.util;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import org.junit.jupiter.api.Disabled;

import java.util.HashSet;
import java.util.Set;

@Disabled
public class FoodCategoryData {

    public static FoodCategory foodCategory1() {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setFoodCategoryName("Food Category 1");
        return foodCategory;
    }

    public static FoodCategory foodCategory2() {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setFoodCategoryName("Food Category 2");
        return foodCategory;
    }

    public static FoodCategory savedFoodCategory() {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setFoodCategoryId(1);
        foodCategory.setFoodCategoryName("Food Category 1");
        return foodCategory;
    }

    public static FoodCategory foodCategoryWithChildEntities() {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setFoodCategoryName("Food Category With Child Entities");
        foodCategory.addFood(FoodData.food1());
        foodCategory.addFood(FoodData.food2());
        return foodCategory;
    }

    public static FoodCategory updatedFoodCategory() {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setFoodCategoryId(1);
        foodCategory.setFoodCategoryName("Updated Category");
        return foodCategory;
    }

    public static FoodCategory foodCategoryWithIdAndName(final Integer foodCategoryId, final String foodCategoryName) {
        final var foodCategory = new FoodCategory(foodCategoryName);
        foodCategory.setFoodCategoryId(foodCategoryId);
        return foodCategory;
    }

    public static Set<FoodCategory> fullFoodCategorySet() {
        final var foodCategorySet = new HashSet<FoodCategory>();
        foodCategorySet.add(updatedFoodCategory());
        foodCategorySet.add(foodCategory2());
        foodCategorySet.add(foodCategoryWithChildEntities());
        return foodCategorySet;
    }

    public static Set<FoodCategory> filteredFoodCategorySet() {
        final var foodCategorySet = new HashSet<FoodCategory>();
        foodCategorySet.add(foodCategory1());
        return foodCategorySet;
    }

    public static Set<IdAndName> foodCategoryIdAndNames() {
        Set<IdAndName> idAndNameSet = new HashSet<>();
        final var foodCategory1 = foodCategory1();
        foodCategory1.setFoodCategoryId(1);
        final var foodCategory2 = foodCategory2();
        foodCategory1.setFoodCategoryId(2);
        final var idAndName1 = new IdAndName(foodCategory1.getFoodCategoryId(), foodCategory1.getFoodCategoryName());
        final var idAndName2 = new IdAndName(foodCategory2.getFoodCategoryId(), foodCategory2.getFoodCategoryName());
        idAndNameSet.add(idAndName1);
        idAndNameSet.add(idAndName2);
        return idAndNameSet;
    }

}
