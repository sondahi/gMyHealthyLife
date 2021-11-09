package com.comert.mhl.database.common;

public interface CRUDExceptional {

    void testPersistEntityExistException();
    void testPersistIllegalArgumentException(); // not null Id
    void testPersistConstraintViolationException(); // duplicate name

    void testFindIllegalArgumentException(); // null Id
    void testFindDoNotReturnNullEntity();

    void testMergeConstraintViolationException(); // duplicate name
    void testMergeIllegalArgumentException(); // removed entity

    void testRemoveIllegalArgumentException(); // null Id
    void testRemoveEntityNotFoundException();

}
