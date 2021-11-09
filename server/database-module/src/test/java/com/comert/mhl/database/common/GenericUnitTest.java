package com.comert.mhl.database.common;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

@Disabled
public abstract class GenericUnitTest {

    private EntityManagerFactory entityManagerFactory;
    protected Cache secondLevelCache;

    @BeforeAll
    protected void setUpTest() {
        entityManagerFactory = Persistence.createEntityManagerFactory("mhltest");
        secondLevelCache = entityManagerFactory.getCache ();
    }

    @AfterAll
    protected void tearDownTest() {
        if(entityManagerFactory!=null && entityManagerFactory.isOpen ()){
            secondLevelCache.evictAll ();
            entityManagerFactory.close();
        }
    }

    protected EntityManager createEntityManager(){
        return entityManagerFactory.createEntityManager ();
    }

    protected abstract void setUpScenario();

    protected abstract void tearDownScenario();

}
