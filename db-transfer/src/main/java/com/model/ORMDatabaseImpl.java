package com.model;

import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ORMDatabaseImpl implements ORMDatabase {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mhltransfer");
    private EntityManager entityManager;

    @Override
    public void open() {
        entityManager = entityManagerFactory.createEntityManager();
        System.out.println("MySQL connection opened");
    }

    @Override
    public void save(FoodCategory foodCategory) {
        entityManager.getTransaction().begin();
        entityManager.persist(foodCategory);
        entityManager.getTransaction().commit();
    }

    @Override
    public void close() {
        if (entityManager != null) {
            if (entityManager.isOpen())
                entityManager.close();
        }
        if (entityManagerFactory != null) {
            if (entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
                System.out.println("MySQL connection closed");
            }
        }
    }
}
