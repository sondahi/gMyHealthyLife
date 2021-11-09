package com.comert.mhl.payment.payer;

import jakarta.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bank {
    BankType bankType();
}
