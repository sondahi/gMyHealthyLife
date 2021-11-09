package com.comert.mhl.database.common.model.entity.component;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class Component {

    @Embedded
    @NotNull
    private Basic basic;
    @Embedded
    @NotNull
    private Vitamin vitamin;
    @Embedded
    @NotNull
    private Mineral mineral;
    @Transient
    private double amount = 100.0;

    protected Component() {
    }

    public Basic getBasic() {
        return this.basic;
    }

    public void setBasic(final Basic basic) {
        this.basic = basic;
    }

    public Vitamin getVitamin() {
        return this.vitamin;
    }

    public void setVitamin(final Vitamin vitamin) {
        this.vitamin = vitamin;
    }

    public Mineral getMineral() {
        return this.mineral;
    }

    public void setMineral(final Mineral mineral) {
        this.mineral = mineral;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

}
