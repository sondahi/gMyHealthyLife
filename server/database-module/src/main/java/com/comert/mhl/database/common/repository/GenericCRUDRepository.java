package com.comert.mhl.database.common.repository;

import com.comert.mhl.database.common.model.entity.GenericEntity;

import java.util.List;

public interface GenericCRUDRepository<E extends GenericEntity> {

    E findEntityById(Long entityId);

    void persistEntity(E entity);

    void refreshEntity(E entity);

    E mergeEntity(E entity);

    void removeEntity(Long entityId);

    List<E> listEntities();

    List<E> listEntities(int firstResult, int maxResult);

}
