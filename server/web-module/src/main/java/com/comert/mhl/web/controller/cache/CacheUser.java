package com.comert.mhl.web.controller.cache;


import com.comert.mhl.database.common.model.dto.IdAndName;

import java.util.Set;

public interface CacheUser {

    Set<IdAndName> getFoodIdAndNames();

    Set<IdAndName> getFoodCategoryIdAndNames();

    Set<IdAndName> getMealIdAndNames();

    Set<IdAndName> getMealCategoryIdAndNames();

}
