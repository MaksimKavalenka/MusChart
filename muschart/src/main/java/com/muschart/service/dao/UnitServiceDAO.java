package com.muschart.service.dao;

import java.util.List;

import com.muschart.bean.entity.IdAndNameEntity;
import com.muschart.entity.UnitEntity;

public interface UnitServiceDAO {

    UnitEntity createUnit(String name);

    UnitEntity getUnitById(long id);

    List<IdAndNameEntity> getAllUnitsIdAndName();

    List<IdAndNameEntity> getTrackUnitsIdAndName(long trackId);

    boolean checkUnitName(String name);

}
