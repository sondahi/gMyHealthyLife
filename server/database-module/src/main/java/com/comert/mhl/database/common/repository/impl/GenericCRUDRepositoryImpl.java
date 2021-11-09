package com.comert.mhl.database.common.repository.impl;

import com.comert.mhl.database.common.exception.EntityNotFoundException;
import com.comert.mhl.database.common.model.entity.GenericEntity;
import com.comert.mhl.database.common.repository.GenericCRUDRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

/*
 * Sub repositories can override methods for the specific behaviours like locking etc.
 */
public abstract class GenericCRUDRepositoryImpl<E extends GenericEntity> implements GenericCRUDRepository<E> {

    protected final static String SELECT = "select e from %s as e order by e.entityId asc";

    @PersistenceContext(unitName = "mhlserver")
    protected EntityManager entityManager;

    @Resource
    protected SessionContext sessionContext;

    private Class<E> entityClass;

    public GenericCRUDRepositoryImpl() {
    }

    /*
     * this constructor can be used for test
     */
    public GenericCRUDRepositoryImpl(EntityManager entityManager, SessionContext sessionContext, Class<E> entityClass) {
        this.entityManager = entityManager;
        this.sessionContext = sessionContext;
        this.entityClass = entityClass;
    }

    protected final String createSelectJpql() {
        String entityName = entityClass.getSimpleName();
        return String.format(SELECT, entityName);
    }

    protected abstract Class<E> getChildClass();

    @PostConstruct
    protected final void onConstruct() {
        this.entityClass = getChildClass();
    }

    @Override
    public E findEntityById(Long entityId) {
        return entityManager.find(entityClass, entityId);
    }

    @Override
    public void persistEntity(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void refreshEntity(E entity) {
        entityManager.refresh(entity);
    }

    @Override
    public E mergeEntity(E entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void removeEntity(Long entityId) throws EntityNotFoundException{
        final E entity = entityManager.getReference(entityClass, entityId);
        try {
            entityManager.remove(entity);
        } catch (jakarta.persistence.EntityNotFoundException exception) {
            sessionContext.setRollbackOnly();
            throw new EntityNotFoundException("FoodCategory might be already removed with Id : ", entityId.toString());
        }
    }

    @Override
    public List<E> listEntities() {
        return entityManager
                .createQuery(createSelectJpql(), entityClass)
                .setHint("org.hibernate.cacheable", "true")
                .getResultList();
    }

    @Override
    public List<E> listEntities(int firstResult, int maxResult) {
        return entityManager
                .createQuery(createSelectJpql(), entityClass)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

}
