package com.comert.mhl.database.common.repository;

import com.comert.mhl.database.common.model.entity.GenericEntity;

import java.util.List;

public interface Childable<T extends GenericEntity> {

    List<T> listAllChildEntities();

}
