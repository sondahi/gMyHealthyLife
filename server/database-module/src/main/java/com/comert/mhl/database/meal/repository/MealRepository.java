package com.comert.mhl.database.meal.repository;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.meal.model.entity.Meal;
import com.comert.mhl.database.meal.service.MealService;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class MealRepository implements MealService {

    public MealRepository() {
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Meal findMealById(Integer mealId) {
        return entityManager.find(Meal.class, mealId);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void saveMeal(Meal entity) {
        entityManager.persist(entity);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public Meal updateMeal(Meal entity) {
        return entityManager.merge(entity);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void deleteMeal(Integer mealId) {
        Meal meal = entityManager.getReference(Meal.class, mealId);
        entityManager.remove(meal);
    }

    @Override
    public Set<Meal> listMeals() {
        Collection<Meal> meals = entityManager
                .createNamedQuery("Meal.listMeals", Meal.class)
                .getResultList();
        return new HashSet<>(meals);
    }

    @Override
    public Set<Meal> listMeals(int firstResult, int maxResult) {
        Collection<Meal> meals = entityManager
                .createNamedQuery("Meal.listMeals", Meal.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
        return new HashSet<>(meals);
    }

    @Override
    public Set<IdAndName> listMealsByIdAndName() {
        Collection<IdAndName> idAndNames = entityManager
                .createNamedQuery("Meal.listMealsByIdAndName", IdAndName.class)
                .getResultList();
        return new HashSet<>(idAndNames);
    }


}
