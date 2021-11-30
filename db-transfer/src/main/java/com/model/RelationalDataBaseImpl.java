package com.model;

import com.comert.mhl.database.common.model.entity.component.Basic;
import com.comert.mhl.database.common.model.entity.component.Mineral;
import com.comert.mhl.database.common.model.entity.component.Vitamin;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RelationalDataBaseImpl implements RelationalDataBase {

    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_URL = "jdbc:derby:HealthyEat;create=true";
    private static final String USER_NAME = "gc";
    private static final String PASSWORD = "5049";
    private Connection connection;

    @Override
    public void open() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            connection.setSchema("GC");
            System.out.println("Apache derby connection opened");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FoodCategory getFoodCategory(int foodCategoryId) throws SQLException {
        FoodCategory foodCategory = getNewFoodCategory(foodCategoryId);
        Set<Integer> foodIds = getFoodIds(foodCategoryId);
        foodIds.forEach(
                (Id) -> {
                    try {
                        foodCategory.addFood(getFood(Id));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
        return foodCategory;
    }

    private FoodCategory getNewFoodCategory(final int foodCategoryId) throws SQLException {
        FoodCategory foodCategory = new FoodCategory();
        final var sql = "select C.NAME from CATEGORY as C where C.ID=?";
        final var preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, foodCategoryId);
        final var resultSet = preparedStatement.executeQuery();
        resultSet.next();
        foodCategory.setFoodCategoryName(resultSet.getString("NAME"));
        return foodCategory;
    }

    private Set<Integer> getFoodIds(final int foodCategoryId) throws SQLException {
        final var sql = "select F_C.FOODID FROM FOOD_CATEGORY AS F_C WHERE F_C.CATEGORYID=?";
        final var preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, foodCategoryId);
        final var resultSet = preparedStatement.executeQuery();
        Set<Integer> foodIds = new HashSet<>();
        while (resultSet.next()) {
            foodIds.add(resultSet.getInt("FOODID"));
        }
        return foodIds;
    }

    private Food getFood(final int foodId) throws SQLException {
        Basic basic = new Basic();
        Vitamin vitamin = new Vitamin();
        Mineral mineral = new Mineral();
        Food food = new Food();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        final var foodNameSQL = "SELECT F.NAME FROM FOOD as F WHERE F.ID=?";
        preparedStatement = connection.prepareStatement(foodNameSQL);
        preparedStatement.setInt(1, foodId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String name = resultSet.getString("NAME");
        food.setFoodName(name);

        final var basicSQL = "SELECT C.NAME, F_C.AMOUNT FROM COMPONENT C, FOOD_COMPONENT F_C WHERE F_C.FOODID = ? AND F_C.COMPONENTID = C.ID AND C.ID IN (?,?,?,?,?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(basicSQL);
        preparedStatement.setInt(1, foodId);
        preparedStatement.setInt(2, 1);//Enerji
        preparedStatement.setInt(3, 4);//Protein
        preparedStatement.setInt(4, 7);//Karbonhidrat
        preparedStatement.setInt(5, 14);//Laktoz
        preparedStatement.setInt(6, 6);//Yağ, toplam
        preparedStatement.setInt(7, 57);//Yağ asidi 18:3 n-3 all-cis
        preparedStatement.setInt(8, 43);//Yağ asitleri, toplam doymuş
        preparedStatement.setInt(9, 58);//Kolesterol
        preparedStatement.setInt(10, 8);//Lif, toplam diyet
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            switch (resultSet.getString("NAME")) {
                case "Enerji":
                    basic.setCalorie(resultSet.getDouble("AMOUNT"));
                    break;
                case "Protein":
                    basic.setProtein(resultSet.getDouble("AMOUNT"));
                    break;
                case "Karbonhidrat":
                    basic.setCarbohydrate(resultSet.getDouble("AMOUNT"));
                    break;
                case "Laktoz":
                    basic.setLactose(resultSet.getDouble("AMOUNT"));
                    break;
                case "Yağ, toplam":
                    basic.setFat(resultSet.getDouble("AMOUNT"));
                    break;
                case "Yağ asidi 18:3 n-3 all-cis":
                    basic.setOmega3(resultSet.getDouble("AMOUNT"));
                    break;
                case "Yağ asitleri, toplam doymuş":
                    basic.setSaturatedFattyAcids(resultSet.getDouble("AMOUNT"));
                    break;
                case "Kolesterol":
                    basic.setCholesterol(resultSet.getDouble("AMOUNT"));
                    break;
                case "Lif, toplam diyet":
                    basic.setFiber(resultSet.getDouble("AMOUNT"));
                    break;
            }
        }
        food.setBasic(basic);

        final var vitaminSQL = "SELECT C.NAME, F_C.AMOUNT FROM COMPONENT C, FOOD_COMPONENT F_C WHERE F_C.FOODID = ? AND F_C.COMPONENTID = C.ID AND C.ID IN (?,?,?,?,?,?,?,?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(vitaminSQL);
        preparedStatement.setInt(1, foodId);
        preparedStatement.setInt(2, 30);//A vitamini
        preparedStatement.setInt(3, 25);//Tiamin
        preparedStatement.setInt(4, 26);//Riboflavin
        preparedStatement.setInt(5, 27);//Niasin
        preparedStatement.setInt(6, 28);//B-6 vitamini, toplam
        preparedStatement.setInt(7, 37);//B-12 vitamini
        preparedStatement.setInt(8, 99);//Folik asit, sentetik
        preparedStatement.setInt(9, 24);//C vitamini
        preparedStatement.setInt(10, 78);//D vitamini, IU
        preparedStatement.setInt(11, 40);//E vitamini, IU
        preparedStatement.setInt(12, 34);//K-1 vitamini
        preparedStatement.setInt(13, 42);//K-2 vitamini
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            switch (resultSet.getString("NAME")) {
                case "A vitamini":
                    vitamin.setVitaminA(resultSet.getDouble("AMOUNT"));
                    break;
                case "Tiamin":
                    vitamin.setVitaminB1(resultSet.getDouble("AMOUNT"));
                    break;
                case "Riboflavin":
                    vitamin.setVitaminB2(resultSet.getDouble("AMOUNT"));
                    break;
                case "Niasin":
                    vitamin.setVitaminB3(resultSet.getDouble("AMOUNT"));
                    break;
                case "B-6 vitamini, toplam":
                    vitamin.setVitaminB6(resultSet.getDouble("AMOUNT"));
                    break;
                case "B-12 vitamini":
                    vitamin.setVitaminB12(resultSet.getDouble("AMOUNT"));
                    break;
                case "Folik asit, sentetik":
                    vitamin.setFolicAcid(resultSet.getDouble("AMOUNT"));
                    break;
                case "C vitamini":
                    vitamin.setVitaminC(resultSet.getDouble("AMOUNT"));
                    break;
                case "D vitamini, IU":
                    vitamin.setVitaminD(resultSet.getDouble("AMOUNT"));
                    break;
                case "E vitamini, IU":
                    vitamin.setVitaminE(resultSet.getDouble("AMOUNT"));
                    break;
                case "K-1 vitamini":
                    vitamin.setVitaminK1(resultSet.getDouble("AMOUNT"));
                    break;
                case "K-2 vitamini":
                    vitamin.setVitaminK2(resultSet.getDouble("AMOUNT"));
                    break;
            }
        }
        food.setVitamin(vitamin);
        final var mineralSQL = "SELECT C.NAME, FC.AMOUNT FROM COMPONENT C, FOOD_COMPONENT FC WHERE FC.FOODID = ? AND FC.COMPONENTID = C.ID AND C.ID IN (?,?,?,?,?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(mineralSQL);
        preparedStatement.setInt(1, foodId);
        preparedStatement.setInt(2, 19);//Kalsiyum, Ca
        preparedStatement.setInt(3, 77);//Iyot, I
        preparedStatement.setInt(4, 17);//Demir, Fe
        preparedStatement.setInt(5, 20);//Magnezyum, Mg
        preparedStatement.setInt(6, 18);//Fosfor, P
        preparedStatement.setInt(7, 21);//Potasyum, K
        preparedStatement.setInt(8, 35);//Selenyum, Se
        preparedStatement.setInt(9, 22);//Sodyum, Na
        preparedStatement.setInt(10, 23);//Çinko, Zn
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            switch (resultSet.getString("NAME")) {
                case "Kalsiyum, Ca":
                    mineral.setCalcium(resultSet.getDouble("AMOUNT"));
                    break;
                case "Iyot, I":
                    mineral.setIodine(resultSet.getDouble("AMOUNT"));
                    break;
                case "Demir, Fe":
                    mineral.setIron(resultSet.getDouble("AMOUNT"));
                    break;
                case "Magnezyum, Mg":
                    mineral.setMagnesium(resultSet.getDouble("AMOUNT"));
                    break;
                case "Fosfor, P":
                    mineral.setPhosphorus(resultSet.getDouble("AMOUNT"));
                    break;
                case "Potasyum, K":
                    mineral.setPotassium(resultSet.getDouble("AMOUNT"));
                    break;
                case "Selenyum, Se":
                    mineral.setSelenium(resultSet.getDouble("AMOUNT"));
                    break;
                case "Sodyum, Na":
                    mineral.setSodium(resultSet.getDouble("AMOUNT"));
                    break;
                case "Çinko, Zn":
                    mineral.setZinc(resultSet.getDouble("AMOUNT"));
                    break;
            }
        }
        food.setMineral(mineral);
        return food;
    }

    @Override
    public void close() {
        try {
            if (connection != null)
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Apache derby connection closed");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
