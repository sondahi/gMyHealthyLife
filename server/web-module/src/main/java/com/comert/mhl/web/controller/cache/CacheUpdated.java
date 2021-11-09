package com.comert.mhl.web.controller.cache;

import jakarta.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface CacheUpdated {

    CacheUpdateProperty property();

}
