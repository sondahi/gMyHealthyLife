package com.comert.mhl.database.common.model.entity.component;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Vitamin implements Serializable {

    @Column(name = "vitaminA", precision = 7, scale = 2)
    private double vitaminA;
    @Column(name = "vitaminB1", precision = 7, scale = 2)
    private double vitaminB1;
    @Column(name = "vitaminB2", precision = 7, scale = 2)
    private double vitaminB2;
    @Column(name = "vitaminB3", precision = 7, scale = 2)
    private double vitaminB3;
    @Column(name = "vitaminB6", precision = 7, scale = 2)
    private double vitaminB6;
    @Column(name = "vitaminB12", precision = 7, scale = 2)
    private double vitaminB12;
    @Column(name = "folicAcid", precision = 7, scale = 2)
    private double folicAcid;
    @Column(name = "vitaminC", precision = 7, scale = 2)
    private double vitaminC;
    @Column(name = "vitaminD", precision = 7, scale = 2)
    private double vitaminD;
    @Column(name = "vitaminE", precision = 7, scale = 2)
    private double vitaminE;
    @Column(name = "vitaminK1", precision = 7, scale = 2)
    private double vitaminK1;
    @Column(name = "vitaminK2", precision = 7, scale = 2)
    private double vitaminK2;

    public Vitamin() {
    }

    public double getVitaminA() {
        return this.vitaminA;
    }

    public void setVitaminA(final double vitaminA) {
        this.vitaminA = vitaminA;
    }

    public double getVitaminB1() {
        return this.vitaminB1;
    }

    public void setVitaminB1(final double vitaminB1) {
        this.vitaminB1 = vitaminB1;
    }

    public double getVitaminB2() {
        return this.vitaminB2;
    }

    public void setVitaminB2(final double vitaminB2) {
        this.vitaminB2 = vitaminB2;
    }

    public double getVitaminB3() {
        return this.vitaminB3;
    }

    public void setVitaminB3(final double vitaminB3) {
        this.vitaminB3 = vitaminB3;
    }

    public double getVitaminB6() {
        return this.vitaminB6;
    }

    public void setVitaminB6(final double vitaminB6) {
        this.vitaminB6 = vitaminB6;
    }

    public double getVitaminB12() {
        return this.vitaminB12;
    }

    public void setVitaminB12(final double vitaminB12) {
        this.vitaminB12 = vitaminB12;
    }

    public double getFolicAcid() {
        return this.folicAcid;
    }

    public void setFolicAcid(final double folicAcid) {
        this.folicAcid = folicAcid;
    }

    public double getVitaminC() {
        return this.vitaminC;
    }

    public void setVitaminC(final double vitaminC) {
        this.vitaminC = vitaminC;
    }

    public double getVitaminD() {
        return this.vitaminD;
    }

    public void setVitaminD(final double vitaminD) {
        this.vitaminD = vitaminD;
    }

    public double getVitaminE() {
        return this.vitaminE;
    }

    public void setVitaminE(final double vitaminE) {
        this.vitaminE = vitaminE;
    }

    public double getVitaminK1() {
        return this.vitaminK1;
    }

    public void setVitaminK1(final double vitaminK1) {
        this.vitaminK1 = vitaminK1;
    }

    public double getVitaminK2() {
        return this.vitaminK2;
    }

    public void setVitaminK2(final double vitaminK2) {
        this.vitaminK2 = vitaminK2;
    }

}
