package com.model;

import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;

public interface ORMDatabase {

    void open();

    void save(FoodCategory foodCategory);

    void close();

}
