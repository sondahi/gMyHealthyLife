package com.comert.mhl.web.controller.cache;


import com.comert.mhl.database.common.model.dto.IdAndName;

import java.util.List;
import java.util.Set;

public interface CacheUser {

    List<IdAndName> getFoodIdAndNames();

    Set<IdAndName> getFoodCategoryIdAndNames();

    List<IdAndName> getMealIdAndNames();

    List<IdAndName> getMealCategoryIdAndNames();

}
