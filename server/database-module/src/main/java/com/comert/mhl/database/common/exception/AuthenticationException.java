package com.comert.mhl.database.common.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class AuthenticationException extends AppException{

    public AuthenticationException(String message, String property) {
        super(message, property);
    }
}
