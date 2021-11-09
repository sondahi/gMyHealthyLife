package com.comert.mhl.web.controller.cache;

import com.comert.mhl.database.common.model.dto.IdAndName;

public enum CacheEvent {

    ADD,
    REMOVE;

    private IdAndName item;

    public IdAndName getItem() {
        return item;
    }

    public void setItem(IdAndName item) {
        this.item = item;
    }

}
