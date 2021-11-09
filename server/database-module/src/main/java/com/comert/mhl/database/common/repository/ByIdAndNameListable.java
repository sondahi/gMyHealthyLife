package com.comert.mhl.database.common.repository;

import com.comert.mhl.database.common.model.dto.IdAndName;

import java.util.List;

public interface ByIdAndNameListable {

    List<IdAndName> listEntitiesByIdAndName();

}
