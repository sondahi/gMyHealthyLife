package com.comert.mhl.web.controller;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.foodcategory.service.FoodCategoryService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Set;

@Named(value = "foodCategoryController")
@ViewScoped
public class FoodCategoryBean implements Serializable {

    private Set<IdAndName> selectableItems;
    private int selectedValue;
    private FoodCategory selectedFoodCategory;

    @EJB
    private FoodCategoryService service;

    public FoodCategoryBean() {
    }

    @PostConstruct
    private void loadItemList() {
        selectableItems = service.listFoodCategoriesByIdAndName();
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
            findFoodCategory();
        } else
            selectedFoodCategory = null;
    }

    public FoodCategory getSelectedFoodCategory() {
        return selectedFoodCategory;
    }

    public void setSelectedFoodCategory(FoodCategory selectedFoodCategory) {
        this.selectedFoodCategory = selectedFoodCategory;
    }

    private void findFoodCategory() {
        selectedFoodCategory = service.findFoodCategoryById(selectedValue);
    }

}
