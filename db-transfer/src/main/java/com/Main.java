package com;

import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.model.ORMDatabase;
import com.model.ORMDatabaseImpl;
import com.model.RelationalDataBase;
import com.model.RelationalDataBaseImpl;

import java.sql.SQLException;

/*
* From relational database to object relational mapping converting
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        RelationalDataBase derby = new RelationalDataBaseImpl();
        ORMDatabase mysql = new ORMDatabaseImpl();

        derby.open();
        mysql.open();

        for (int i = 1; i <15 ; i++) {
            FoodCategory foodCategory = derby.getFoodCategory(i);
            mysql.save(foodCategory);
        }

        derby.close();
        mysql.close();
    }
}
