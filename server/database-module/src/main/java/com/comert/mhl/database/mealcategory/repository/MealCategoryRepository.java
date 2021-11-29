package com.comert.mhl.database.mealcategory.repository;

import com.comert.mhl.database.common.model.dto.IdAndName;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import com.comert.mhl.database.mealcategory.service.MealCategoryService;
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
public class MealCategoryRepository implements MealCategoryService {

    @Resource
    private SessionContext sessionContext;

    @PersistenceContext
    private EntityManager entityManager;


    public MealCategoryRepository() {
    }

    public MealCategoryRepository(SessionContext sessionContext, EntityManager entityManager) {
        this.sessionContext = sessionContext;
        this.entityManager = entityManager;
    }

    @Override
    public MealCategory findMealCategoryById(Integer mealCategoryId) {
        return entityManager.find(MealCategory.class, mealCategoryId);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void saveMealCategory(MealCategory mealCategory) {
        entityManager.persist(mealCategory);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public MealCategory updateMealCategory(MealCategory mealCategory) {
        return entityManager.merge(mealCategory);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void deleteMealCategory(final Integer mealCategoryId) {
        final MealCategory toDeleteMealCategory = entityManager.getReference(MealCategory.class,mealCategoryId);
        entityManager.remove(toDeleteMealCategory);
    }

    @Override
    public Set<MealCategory> listMealCategories() {
        Collection<MealCategory> mealCategories = entityManager
                .createNamedQuery("MealCategory.listMealCategories", MealCategory.class)
                .getResultList();
        return new HashSet<>(mealCategories);
    }

    @Override
    public Set<MealCategory> listMealCategories(int firstResult, int maxResult) {
        Collection<MealCategory> mealCategories = entityManager
                .createNamedQuery("MealCategory.listMealCategories", MealCategory.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
        return new HashSet<>(mealCategories);
    }

    @Override
    public Set<IdAndName> listMealCategoriesByIdAndName() {
        Collection<IdAndName> idAndNames = entityManager
                .createNamedQuery("MealCategory.listMealCategoriesByIdAndName", IdAndName.class)
                .getResultList();
        return new HashSet<>(idAndNames);
    }


}
