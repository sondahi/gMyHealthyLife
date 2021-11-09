package com.comert.mhl.database.common.model.entity;

import com.comert.mhl.database.common.model.listener.PersistenceTestListener;
import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
//@EntityListeners(PersistenceListener.class) // gerçek
//@EntityListeners(PersistenceTestListener.class) // test için

public abstract class GenericEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long entityId;
    @Version
    protected int version;

    protected GenericEntity() {
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
