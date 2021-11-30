package com.model;

import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;

import java.sql.SQLException;

public interface RelationalDataBase {
    void open();

    FoodCategory getFoodCategory(int foodCategoryId) throws SQLException;

    void close();
}
