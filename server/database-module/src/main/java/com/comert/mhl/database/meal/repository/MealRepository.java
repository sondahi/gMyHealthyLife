package com.comert.mhl.database.meal.repository;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.meal.model.entity.Meal;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class MealRepository {

    public MealRepository() {
    }

    @PersistenceContext
    private EntityManager entityManager;

    public Meal findMealById(Integer mealId) {
        return entityManager.find(Meal.class, mealId);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void persistEntity(Meal entity) {
        entityManager.persist(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public Meal mergeEntity(Meal entity) {
        return entityManager.merge(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void refreshEntity(Meal entity) {
        entityManager.refresh(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void removeEntity(Meal entity) {
        entityManager.remove(entity);
    }

    public List<Meal> listMeals() {
        return entityManager
                .createNamedQuery("Meal.listMeals", Meal.class)
                .getResultList();
    }

    public List<Meal> listMeals(int firstResult, int maxResult) {
        return entityManager
                .createNamedQuery("Meal.listMeals", Meal.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

    public List<IdAndName> listMealsByIdAndName() {
        return entityManager
                .createNamedQuery("Meal.listMealsByIdAndName", IdAndName.class)
                .getResultList();
    }


}
