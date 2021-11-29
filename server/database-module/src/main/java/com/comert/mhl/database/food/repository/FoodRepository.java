package com.comert.mhl.database.food.repository;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.food.service.FoodService;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class FoodRepository implements FoodService {


    @Resource
    private SessionContext sessionContext;

    @PersistenceContext
    private EntityManager entityManager;


    public FoodRepository() {
    }

    public FoodRepository(final EntityManager entityManager, final SessionContext sessionContext) {
        this.entityManager = entityManager;
        this.sessionContext = sessionContext;
    }

    @Override
    public Food findFoodById(Integer foodId) {
        return entityManager.find(Food.class, foodId);
    }

    @Override
    public void saveFood(Food food) {
        entityManager.persist(food);
    }

    @Override
    public Food updateFood(Food food) {
        return entityManager.merge(food);
    }

    @Override
    public void removeFood(Integer foodId) {
        final var foodCategory = entityManager.getReference(Food.class, foodId);
        entityManager.remove(foodCategory);
    }

    @Override
    public Set<Food> listFoods() {
        Collection<Food> foods = entityManager
                .createNamedQuery("Food.listFoods", Food.class)
                .getResultList();
        return new HashSet<>(foods);
    }

    @Override
    public Set<Food> listFoods(int firstResult, int maxResult) {
        Collection<Food> foods = entityManager
                .createNamedQuery("Food.listFoods", Food.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
        return new HashSet<>(foods);
    }

    @Override
    public Set<IdAndName> listFoodsByIdAndName() {
        Collection<IdAndName> idAndNames = entityManager
                .createNamedQuery("Food.listFoodsByIdAndName", IdAndName.class)
                .getResultList();
        return new HashSet<>(idAndNames);
    }
}
