package com.comert.mhl.database.member.model.entity;

import com.comert.mhl.database.food.model.entity.Food;

import java.lang.reflect.Field;

public interface FoodTransfer {

    default void transferComponent(Food food){
        try {
            Field basicField = getClass().getSuperclass().getDeclaredField("basic");
            Field vitaminField = getClass().getSuperclass().getDeclaredField("vitamin");
            Field mineralField = getClass().getSuperclass().getDeclaredField("mineral");
            basicField.setAccessible(true);
            vitaminField.setAccessible(true);
            mineralField.setAccessible(true);
            basicField.set(this,food.getBasic());
            vitaminField.set(this,food.getVitamin());
            mineralField.set(this,food.getMineral());
            basicField.setAccessible(false);
            vitaminField.setAccessible(false);
            mineralField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
