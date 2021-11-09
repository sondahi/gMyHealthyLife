package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class EntityFieldNotValidException extends AppException {

    public EntityFieldNotValidException(String message, String property) {
        super(message, property);
    }
}
