package com.comert.mhl.database.common.util;

import com.comert.mhl.database.common.model.entity.component.Basic;
import com.comert.mhl.database.common.model.entity.component.Mineral;
import com.comert.mhl.database.common.model.entity.component.Vitamin;
import com.comert.mhl.database.food.model.entity.Food;
import org.junit.jupiter.api.Disabled;

@Disabled
public class FoodData {

    public static Food food1() {
        Food food = new Food ();
        food.setFoodName ( "Food 1" );
        food.setLogoPath("img/food1");

        Basic basic = new Basic ();
        basic.setCalorie ( 127.23 );
        basic.setProtein (34.34);
        basic.setCarbohydrate ( 883 );
        basic.setLactose ( 11 );
        basic.setFat ( 64 );
        basic.setOmega3 ( 132.34 );
        basic.setSaturatedFattyAcids ( 342.4 );
        basic.setCholesterol ( 122.4);
        basic.setFiber (564.4);
        food.setBasic ( basic );

        Vitamin vitamin = new Vitamin ();
        vitamin.setVitaminA ( 34.6 );
        vitamin.setVitaminB1 ( 42342.232 );
        vitamin.setVitaminB2 ( 414.11 );
        vitamin.setVitaminB3 ( 34234.5 );
        vitamin.setVitaminB6 (4124.5  );
        vitamin.setVitaminB12 ( 1111.34 );
        vitamin.setFolicAcid (4141.22  );
        vitamin.setVitaminC ( 1243124.22 );
        vitamin.setVitaminD ( 14324.44);
        vitamin.setVitaminE (0.88 );
        vitamin.setVitaminK1 ( 412341.22);
        vitamin.setVitaminK2 ( 123423.22 );
        food.setVitamin ( vitamin );

        Mineral mineral = new Mineral ();
        mineral.setCalcium ( 324234.4 );
        mineral.setIodine ( 23411.3 );
        mineral.setIron ( 34823.4 );
        mineral.setMagnesium ( 757.4 );
        mineral.setPhosphorus ( 123123.4 );
        mineral.setPotassium ( 26334.3 );
        mineral.setSelenium (234234.22  );
        mineral.setSodium ( 234141.3 );
        mineral.setZinc (134234.3  );
        food.setMineral ( mineral );
        return food;

    }

    public static Food food2() {
        Food food = new Food ();
        food.setFoodName ( "Food 2" );
        food.setLogoPath("img/food2");

        Basic basic = new Basic ();
        basic.setCalorie ( 437.23 );
        basic.setProtein (343.34);
        basic.setCarbohydrate ( 183 );
        basic.setLactose ( 211 );
        basic.setFat ( 644.4 );
        basic.setOmega3 ( 532.34 );
        basic.setSaturatedFattyAcids ( 142.4 );
        basic.setCholesterol ( 1232.4);
        basic.setFiber (564.44);
        food.setBasic ( basic );

        Vitamin vitamin = new Vitamin ();
        vitamin.setVitaminA ( 34.633 );
        vitamin.setVitaminB1 ( 42342.231 );
        vitamin.setVitaminB2 ( 414.11 );
        vitamin.setVitaminB3 ( 34.5 );
        vitamin.setVitaminB6 (414.5  );
        vitamin.setVitaminB12 ( 3411.34 );
        vitamin.setFolicAcid (41441.22  );
        vitamin.setVitaminC ( 0.22 );
        vitamin.setVitaminD ( 314324.44);
        vitamin.setVitaminE (30.88 );
        vitamin.setVitaminK1 ( 341.22);
        vitamin.setVitaminK2 ( 423.22 );
        food.setVitamin ( vitamin );

        Mineral mineral = new Mineral ();
        mineral.setCalcium ( 0.4434);
        mineral.setIodine ( 311.3 );
        mineral.setIron ( 3323.4 );
        mineral.setMagnesium ( 7574.4 );
        mineral.setPhosphorus ( 1123.4445454 );
        mineral.setPotassium ( 23.3 );
        mineral.setSelenium (653424.223  );
        mineral.setSodium ( 54141.3 );
        mineral.setZinc (6234.3  );
        food.setMineral ( mineral );
        return food;
    }

    public static Food foodWithId(Integer foodId, Food food){
        food.setFoodId(foodId);
        return food;
    }

}
