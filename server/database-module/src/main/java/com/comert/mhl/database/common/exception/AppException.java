package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class AppException extends RuntimeException {

    private String property;

    public AppException(String message, String property) {
        super(message);
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
