package com.comert.mhl.database.member.model.entity;

import com.comert.mhl.database.meal.model.entity.Meal;

import java.lang.reflect.Field;

public interface MealTransfer extends FoodTransfer {

    default void transferComponent(Meal meal){
        try {
            Field basicField = getClass().getSuperclass().getDeclaredField("basic");
            Field vitaminField = getClass().getSuperclass().getDeclaredField("vitamin");
            Field mineralField = getClass().getSuperclass().getDeclaredField("mineral");
            basicField.setAccessible(true);
            vitaminField.setAccessible(true);
            mineralField.setAccessible(true);
            basicField.set(this,meal.getBasic());
            vitaminField.set(this,meal.getVitamin());
            mineralField.set(this,meal.getMineral());
            basicField.setAccessible(false);
            vitaminField.setAccessible(false);
            mineralField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
