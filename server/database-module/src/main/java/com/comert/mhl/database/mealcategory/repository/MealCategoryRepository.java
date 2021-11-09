package com.comert.mhl.database.mealcategory.repository;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.common.repository.ByIdAndNameListable;
import com.comert.mhl.database.common.repository.impl.GenericCRUDRepositoryImpl;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class MealCategoryRepository{

    private final String LIST_MEALCATEGORIES_BY_ID_AND_NAME = "select new com.comert.mhl.database.common.model.dto.IdAndName(mg.mealCategoryId,mg.mealCategoryName)" +
            " from MealCategory mg";

    public MealCategoryRepository() {
    }

    @PersistenceContext
    private EntityManager entityManager;

    protected Class<MealCategory> getChildClass() {
        return MealCategory.class;
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void persistEntity(MealCategory entity) {

        entityManager.persist(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public MealCategory mergeEntity(MealCategory entity) {
        return entityManager.merge(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void refreshEntity(MealCategory entity) {
        entityManager.refresh(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void removeEntity(MealCategory entity) {
        entityManager.remove(entity);
    }

    public List<IdAndName> listEntitiesByIdAndName() {
        Query query = entityManager.createQuery(LIST_MEALCATEGORIES_BY_ID_AND_NAME);
        return query.getResultList();
    }
}
