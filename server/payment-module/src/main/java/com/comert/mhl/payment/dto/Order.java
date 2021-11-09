package com.comert.mhl.payment.dto;

import java.io.Serializable;

public class Order implements Serializable {

    private double total;
    private String currency;
    private String method; // burada BANK CREDIT_CARD vs gibi alterntiflerde var
    private String intent;
    private String description;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
