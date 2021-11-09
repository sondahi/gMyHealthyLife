package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class EntityNotSavedException extends AppException{

    public EntityNotSavedException(String message, String property) {
        super(message, property);
    }
}
