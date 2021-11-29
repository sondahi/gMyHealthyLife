package com.comert.mhl.database.common;

public interface CRUDTest {

    void testPersistAndFindAndGetReferenceEntity(final Object o);

    void testMergeEntity(final Object o);

    void testRefreshEntity(final Object o);

    void testRemoveEntity(final Object o);

    void testListEntities();

    void testListEntitiesByIdAndName();

}
