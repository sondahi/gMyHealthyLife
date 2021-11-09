package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class EntityNotFoundException extends AppException {

    public EntityNotFoundException(String message, String property) {
        super(message, property);
    }

}
