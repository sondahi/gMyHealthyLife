package com.comert.mhl.database.common.model.entity.component;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Mineral implements Serializable {

    @Column(name = "calcium", precision = 7, scale = 2)
    private double calcium; //Kalsiyum
    @Column(name = "iodine", precision = 7, scale = 2)
    private double iodine; //İyot
    @Column(name = "iron", precision = 7, scale = 2)
    private double iron; //Demir
    @Column(name = "magnesium", precision = 7, scale = 2)
    private double magnesium; //Magenzyum
    @Column(name = "phosphorus", precision = 7, scale = 2)
    private double phosphorus; //Fosfor
    @Column(name = "potassium", precision = 7, scale = 2)
    private double potassium; //Potasyum
    @Column(name = "selenium", precision = 7, scale = 2)
    private double selenium;//Selenyum
    @Column(name = "sodium", precision = 7, scale = 2)
    private double sodium;//Sodyum
    @Column(name = "zinc", precision = 7, scale = 2)
    private double zinc; //Çinko

    public Mineral() {
    }

    public double getCalcium() {
        return this.calcium;
    }

    public void setCalcium(final double calcium) {
        this.calcium = calcium;
    }

    public double getIodine() {
        return this.iodine;
    }

    public void setIodine(final double iodine) {
        this.iodine = iodine;
    }

    public double getIron() {
        return this.iron;
    }

    public void setIron(final double iron) {
        this.iron = iron;
    }

    public double getMagnesium() {
        return this.magnesium;
    }

    public void setMagnesium(final double magnesium) {
        this.magnesium = magnesium;
    }

    public double getPhosphorus() {
        return this.phosphorus;
    }

    public void setPhosphorus(final double phosphorus) {
        this.phosphorus = phosphorus;
    }

    public double getPotassium() {
        return this.potassium;
    }

    public void setPotassium(final double potassium) {
        this.potassium = potassium;
    }

    public double getSelenium() {
        return this.selenium;
    }

    public void setSelenium(final double selenium) {
        this.selenium = selenium;
    }

    public double getSodium() {
        return this.sodium;
    }

    public void setSodium(final double sodium) {
        this.sodium = sodium;
    }

    public double getZinc() {
        return this.zinc;
    }

    public void setZinc(final double zinc) {
        this.zinc = zinc;
    }

}
