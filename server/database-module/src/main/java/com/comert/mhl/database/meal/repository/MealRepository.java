package com.comert.mhl.database.meal.repository;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.common.repository.ByIdAndNameListable;
import com.comert.mhl.database.common.repository.impl.GenericCRUDRepositoryImpl;
import com.comert.mhl.database.meal.model.entity.Meal;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class MealRepository{

    private final String LIST_MEALS_BY_ID_AND_NAME = "select new com.comert.mhl.database.common.model.dto.IdAndName(m.mealId,m.mealName)" +
            " from Meal m";


    public MealRepository() {
    }

    @PersistenceContext
    private EntityManager entityManager;

    protected Class<Meal> getChildClass() {
        return Meal.class;
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

    public List<IdAndName> listEntitiesByIdAndName() {
        Query query = entityManager.createQuery(LIST_MEALS_BY_ID_AND_NAME);
        return query.getResultList();
    }
}
