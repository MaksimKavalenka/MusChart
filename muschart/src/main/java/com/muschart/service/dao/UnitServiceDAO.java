package com.muschart.service.dao;

import java.util.List;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.UnitEntity;

public interface UnitServiceDAO {

    UnitEntity createUnit(String name);

    UnitEntity getUnitById(long id);

    List<IdAndNameDTO> getAllUnitsIdAndName();

    List<IdAndNameDTO> getTrackUnitsIdAndName(long trackId);

    boolean checkUnitName(String name);

}