package com.comert.mhl.database.common;

import jakarta.persistence.EntityManager;
import org.hibernate.jpa.QueryHints;
import org.junit.jupiter.api.Disabled;

import java.util.List;

@Disabled
public class TransactionalCommandExecutor {

    private final EntityManager entityManager;

    public TransactionalCommandExecutor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void clearDataBase(Class<?>... entityClasses) {
        final var secondLevelCache = entityManager.getEntityManagerFactory().getCache();
        try {
            entityManager.getTransaction().begin();
            List.of(entityClasses)
                    .forEach(entityClass -> entityManager
                            .createQuery("delete from " + entityClass.getSimpleName())
                            .setHint(QueryHints.HINT_CACHEABLE, true)
                            .executeUpdate()
                    );
            entityManager.getTransaction().commit();
        } catch (Throwable throwable) { // execute rollback and return again
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            else
                throw new TestException(throwable);
            throw throwable;
        } finally {
            secondLevelCache.evictAll();
        }
    }

    // persist, remove
    public void executeTransactionalCommand(final VoidCommand command) {
        try {
            entityManager.getTransaction().begin();
            command.execute();
            entityManager.getTransaction().commit();
        } catch (final Throwable throwable) { // execute rollback and return again
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            else
                throw new TestException(throwable);
            throw throwable;
        }
    }

    // merge
    public <T> T executeTransactionalCommand(final TypeCommand<T> command) {
        try {
            entityManager.getTransaction().begin();
            T toReturn = command.execute();
            entityManager.getTransaction().commit();
            return toReturn;
        } catch (final Throwable throwable) { // execute rollback and return again
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            else
                throw new TestException(throwable);
            throw throwable;
        }
    }

}