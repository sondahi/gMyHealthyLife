package com.comert.mhl.database.common.model.dto;

public class ExceptionMessage {

    private final String message;
    private final String property;

    public ExceptionMessage(String message, String property) {
        this.message = message;
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public String getProperty() {
        return property;
    }

}
