package com.comert.mhl.database.common.model.listener;

import com.comert.mhl.database.common.model.entity.GenericEntity;
import jakarta.persistence.*;

public class PersistenceTestListener {

    private static void test(String template, Object... arguments) {
        System.out.printf(template, arguments);
        System.out.println();
    }

    @PrePersist
    public void onPrePersist(GenericEntity baseEntity) {
        test("Entity : %s, EntityId : %d persisting", baseEntity.getClass().getSimpleName(), baseEntity.getEntityId());
    }

    @PostPersist
    public void onPostPersist(GenericEntity baseEntity) {
        test("Entity : %s, EntityId : %d persisted", baseEntity.getClass().getSimpleName(), baseEntity.getEntityId());
    }

    @PreUpdate
    public void onPreUpdate(GenericEntity baseEntity) {
        test("Entity : %s, EntityId : %d updating", baseEntity.getClass().getSimpleName(), baseEntity.getEntityId());
    }

    @PostUpdate
    public void onPostUpdate(GenericEntity baseEntity) {
        test("Entity : %s, EntityId : %d updated", baseEntity.getClass().getSimpleName(), baseEntity.getEntityId());
    }

    @PreRemove
    public void onPreRemove(GenericEntity baseEntity) {
        test("Entity : %s, EntityId : %d removing", baseEntity.getClass().getSimpleName(), baseEntity.getEntityId());
    }

    @PostRemove
    public void onPostRemove(GenericEntity baseEntity) {
        test("Entity : %s, EntityId : %d removed", baseEntity.getClass().getSimpleName(), baseEntity.getEntityId());
    }

}
