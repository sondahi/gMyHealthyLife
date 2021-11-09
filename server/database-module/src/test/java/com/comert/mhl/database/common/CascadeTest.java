package com.comert.mhl.database.common;

public interface CascadeTest {

    void testPersistEntityWithChildEntities();

    void testMergeEntityWithChildEntities();

    void testRefreshEntityWithChildEntities();

    void testOrphanRemovalEntityFromChildEntities();

    void testDetachEntityWithChildEntities();

    void testRemoveEntityWithChildEntities();

}
