package com.comert.mhl.web.controller;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.food.service.FoodService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Set;

@Named(value = "foodController")
@ViewScoped
public class FoodBean implements Serializable {

    private Set<IdAndName> selectableItems;
    private int selectedValue;
    private Food selectedFood;
    private boolean logoRendered;
    private String foodLogoPath;

    @EJB
    private FoodService service;

    public FoodBean() {
    }

    @PostConstruct
    private void loadItemList() {
        selectableItems = service.listFoodsByIdAndName();
    }

    public Set<IdAndName> getSelectableItems() {
        return selectableItems;
    }

    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
        if (selectedValue != 0) {
            findFood();
            logoRendered = true;
        } else
            selectedFood = null;
        logoRendered = false;
    }

    public Food getSelectedFood() {
        return selectedFood;
    }

    public void setSelectedFood(Food selectedFood) {
        this.selectedFood = selectedFood;
    }

    public boolean isLogoRendered() {
        return logoRendered;
    }

    public void setLogoRendered(boolean logoRendered) {
        this.logoRendered = logoRendered;
    }

    public String getFoodLogoPath() {
        return foodLogoPath;
    }

    private void findFood() {
        selectedFood = service.findFoodById(selectedValue);
        foodLogoPath = "/resources/logos/foods/" + selectedFood.getLogoPath();
    }


}
