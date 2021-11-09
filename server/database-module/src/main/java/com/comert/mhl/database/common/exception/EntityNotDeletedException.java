package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class EntityNotDeletedException extends AppException{

    public EntityNotDeletedException(String message, String property) {
        super(message, property);
    }
}
