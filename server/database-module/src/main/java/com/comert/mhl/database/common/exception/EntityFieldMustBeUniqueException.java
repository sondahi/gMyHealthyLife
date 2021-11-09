package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class EntityFieldMustBeUniqueException extends AppException {

    public EntityFieldMustBeUniqueException(String message, String property) {
        super(message, property);
    }
}
