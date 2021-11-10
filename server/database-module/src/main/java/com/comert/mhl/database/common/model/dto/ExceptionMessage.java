package com.comert.mhl.database.common.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ExceptionMessage {

    private String message;
    private String property;

    public ExceptionMessage() {
    }

    public ExceptionMessage(String message, String property) {
        this.message = message;
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .append("property", property)
                .toString();
    }
}
