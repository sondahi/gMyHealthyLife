package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class EntityNotUpdatedException extends AppException{

    public EntityNotUpdatedException(String message, String property) {
        super(message, property);
    }
}
