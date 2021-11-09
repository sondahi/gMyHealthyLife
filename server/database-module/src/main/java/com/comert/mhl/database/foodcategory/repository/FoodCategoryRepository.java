package com.comert.mhl.database.foodcategory.repository;

import com.comert.mhl.database.common.exception.EntityNotDeletedException;
import com.comert.mhl.database.common.exception.EntityNotFoundException;
import com.comert.mhl.database.common.exception.EntityNotSavedException;
import com.comert.mhl.database.common.exception.EntityNotUpdatedException;
import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.foodcategory.service.FoodCategoryService;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class FoodCategoryRepository implements FoodCategoryService {

    @Resource
    private SessionContext sessionContext;

    @PersistenceContext
    private EntityManager entityManager;

    public FoodCategoryRepository() {
    }

    public FoodCategoryRepository(EntityManager entityManager, SessionContext sessionContext) {
        this.entityManager = entityManager;
        this.sessionContext = sessionContext;
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    @Override
    public void saveFoodCategory(FoodCategory foodCategory) throws EntityNotSavedException {
        try {
            entityManager.persist(foodCategory);
            entityManager.flush();
        } catch (PersistenceException exception) {
            sessionContext.setRollbackOnly();
            if (exception.getCause() != null && exception.getCause() instanceof ConstraintViolationException) {
                throw new EntityNotSavedException("Field must be unique", "foodCategoryName");
            } else
                throw exception;
        }
    }

    @Override
    public FoodCategory findFoodCategoryById(Integer foodCategoryId) {
        return entityManager.find(FoodCategory.class, foodCategoryId);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    @Override
    public FoodCategory updateFoodCategory(FoodCategory foodCategory) throws EntityNotUpdatedException {
        try {
            final var toReturnFoodCategory = entityManager.merge(foodCategory);
            if (!Objects.equals(toReturnFoodCategory.getFoodCategoryId(), foodCategory.getFoodCategoryId()))
                throw new IllegalArgumentException();
            entityManager.flush();
            return toReturnFoodCategory;
        } catch (PersistenceException | IllegalArgumentException exception) {
            sessionContext.setRollbackOnly();
            if (exception instanceof PersistenceException) {
                if (exception.getCause() != null && exception.getCause() instanceof ConstraintViolationException)
                    throw new EntityNotUpdatedException("Field must be unique", "foodCategoryName");
                else
                    throw exception;
            } else {
                throw new EntityNotUpdatedException("FoodCategory might have already been deleted", foodCategory.getFoodCategoryName());
            }
        }
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void deleteFoodCategory(Integer foodCategoryId) throws EntityNotDeletedException {
        final var foodCategory = entityManager.getReference(FoodCategory.class, foodCategoryId);

        try {
            entityManager.remove(foodCategory);
            entityManager.flush();
        } catch (PersistenceException exception) {
            sessionContext.setRollbackOnly();
            if (exception instanceof jakarta.persistence.EntityNotFoundException)
                throw new EntityNotDeletedException("FoodCategory might have already been deleted", foodCategoryId.toString());
            else
                throw exception;
        }
    }

    @Override
    public Set<FoodCategory> listFoodCategories() {
        Collection<FoodCategory> foodCategories = entityManager
                .createNamedQuery("FoodCategory.listFoodCategories", FoodCategory.class)
                .getResultList();
        return new HashSet<>(foodCategories);
    }

    @Override
    public Set<FoodCategory> listFoodCategories(int firstResult, int maxResult) {
        Collection<FoodCategory> foodCategories = entityManager
                .createNamedQuery("FoodCategory.listFoodCategories", FoodCategory.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
        return new HashSet<>(foodCategories);
    }

    @Override
    public Set<IdAndName> listFoodCategoriesByIdAndName() {
        Collection<IdAndName> idAndNames = entityManager
                .createNamedQuery("FoodCategory.listFoodCategoriesByIdAndName", IdAndName.class)
                .getResultList();
        return new HashSet<>(idAndNames);
    }

}
