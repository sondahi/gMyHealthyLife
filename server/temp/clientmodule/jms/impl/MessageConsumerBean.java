package com.comert.mhl.database.jms.impl;

import com.comert.mhl.database.common.exception.EntityFieldMustBeUniqueException;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.foodcategory.service.FoodCategoryService;
import com.comert.mhl.database.meal.model.entity.Meal;
import com.comert.mhl.database.mealcategory.service.MealCategoryService;
import com.comert.mhl.database.meal.service.MealService;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import com.comert.mhl.database.member.model.entity.Member;
import com.comert.mhl.database.food.service.FoodService;

import com.comert.mhl.database.member.service.MemberService;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/DATABASEQ"),
        }
)
public class MessageConsumerBean implements MessageListener {

    @EJB
    private MemberService memberService;

    @EJB
    private MealCategoryService mealCategoryService;

    @EJB
    private MealService mealService;

    @EJB
    private FoodService foodService;

    @EJB
    private FoodCategoryService foodCategoryService;

    @Override
    public void onMessage(Message message) { // design pattern ve cdi kullanılacak
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Object object = null;
            try {
                object = objectMessage.getObject();
                String command = null;
                if (object != null) {
                    if (object instanceof Member) {
                        command = objectMessage.getStringProperty("Member");
                        Member member = (Member) object;
                        switch (command) {
                            case "persist":
                                memberService.persistEntity(member);
                                break;
                            case "merge":
                                memberService.mergeEntity(member);
                                break;
                            case "remove":
                                memberService.removeEntity(member);
                                break;
                        }
                    } else if (object instanceof Meal) {
                        command = objectMessage.getStringProperty("Meal");
                        Meal meal = (Meal) object;
                        switch (command) {
                            case "persist":
                                mealService.persistEntity(meal);
                                break;
                            case "merge":
                                mealService.mergeEntity(meal);
                                break;
                            case "remove":
                                mealService.removeEntity(meal);
                                break;
                        }
                    } else if (object instanceof MealCategory) {
                        command = objectMessage.getStringProperty("MealCategory");
                        MealCategory mealCategory = (MealCategory) object;
                        switch (command) {
                            case "persist":
                                mealCategoryService.persistEntity(mealCategory);
                                break;
                            case "merge":
                                mealCategoryService.mergeEntity(mealCategory);
                                break;
                            case "remove":
                                mealCategoryService.removeEntity(mealCategory);
                                break;
                        }
                    } else if (object instanceof Food) {
                        command = objectMessage.getStringProperty("Food");
                        Food food = (Food) object;
                        switch (command) {
                            case "persist":
                                foodService.persistEntity(food);
                                break;
                            case "merge":
                                foodService.mergeEntity(food);
                                break;
                            case "remove":
                                foodService.removeEntity(food);
                                break;
                        }
                    } else if (object instanceof FoodCategory) {
                        command = objectMessage.getStringProperty("FoodCategory");
                        FoodCategory foodCategory = (FoodCategory) object;
                        switch (command) {
                            case "persist":
                                foodCategoryService.saveFoodCategory(foodCategory);
                                break;
                            case "merge":
                                foodCategoryService.updateFoodCategory(foodCategory);
                                break;
                            case "remove":
                                foodCategoryService.removeFoodCategory(foodCategory);
                                break;
                        }
                    }
                }
            } catch (JMSException | EntityFieldMustBeUniqueException e) {
                System.out.println (e.getMessage () );
            }

        } else if(message instanceof TextMessage){
            try {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
