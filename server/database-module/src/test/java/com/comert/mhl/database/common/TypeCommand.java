package com.comert.mhl.database.common;

import org.junit.jupiter.api.Disabled;

@Disabled
@FunctionalInterface
public interface TypeCommand<T>{
    T execute();
}
