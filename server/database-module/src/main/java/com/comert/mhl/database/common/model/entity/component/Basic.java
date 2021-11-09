package com.comert.mhl.database.common.model.entity.component;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Basic implements Serializable {

    @Column(name = "calorie", precision = 7, scale = 2)
    private double calorie;
    @Column(name = "protein", precision = 7, scale = 2)
    private double protein;
    @Column(name = "carbohydrate", precision = 7, scale = 2)
    private double carbohydrate;
    @Column(name = "lactose", precision = 7, scale = 2)
    private double lactose;
    @Column(name = "fat", precision = 7, scale = 2)
    private double fat;
    @Column(name = "omega3", precision = 7, scale = 2)
    private double omega3;
    @Column(name = "saturatedFattyAcids", precision = 7, scale = 2)
    private double saturatedFattyAcids;
    @Column(name = "cholesterol", precision = 7, scale = 2)
    private double cholesterol;
    @Column(name = "fiber", precision = 7, scale = 2)
    private double fiber;

    public Basic() {
    }

    public double getCalorie() {
        return this.calorie;
    }

    public void setCalorie(final double calorie) {
        this.calorie = calorie;
    }

    public double getProtein() {
        return this.protein;
    }

    public void setProtein(final double protein) {
        this.protein = protein;
    }

    public double getCarbohydrate() {
        return this.carbohydrate;
    }

    public void setCarbohydrate(final double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getLactose() {
        return this.lactose;
    }

    public void setLactose(final double lactose) {
        this.lactose = lactose;
    }

    public double getFat() {
        return this.fat;
    }

    public void setFat(final double fat) {
        this.fat = fat;
    }

    public double getOmega3() {
        return this.omega3;
    }

    public void setOmega3(final double omega3) {
        this.omega3 = omega3;
    }

    public double getSaturatedFattyAcids() {
        return this.saturatedFattyAcids;
    }

    public void setSaturatedFattyAcids(final double saturatedFattyAcids) {
        this.saturatedFattyAcids = saturatedFattyAcids;
    }

    public double getCholesterol() {
        return this.cholesterol;
    }

    public void setCholesterol(final double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getFiber() {
        return this.fiber;
    }

    public void setFiber(final double fiber) {
        this.fiber = fiber;
    }

}
