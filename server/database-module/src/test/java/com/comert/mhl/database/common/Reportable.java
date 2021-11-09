package com.comert.mhl.database.common;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

@Disabled
public interface Reportable {

    @BeforeEach
    default void startTime(TestInfo info, TestReporter reporter ){
        reporter.publishEntry ( info.getDisplayName ()+" Begin Time: "+ LocalDateTime.now () );
    }

    @AfterEach
    default void stopTime(TestInfo info, TestReporter reporter){
        reporter.publishEntry ( info.getDisplayName ()+" End Time: "+ LocalDateTime.now () );
    }

}
