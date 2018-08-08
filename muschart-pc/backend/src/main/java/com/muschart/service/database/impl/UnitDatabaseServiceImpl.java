package com.muschart.service.database.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.UnitEntity;
import com.muschart.service.database.dao.UnitDatabaseServiceDAO;
import com.muschart.utility.Parser;

@Service("unitDatabaseService")
public class UnitDatabaseServiceImpl extends DatabaseServiceImpl implements UnitDatabaseServiceDAO {

    @Override
    public UnitEntity createUnit(String name) {
        UnitEntity unit = new UnitEntity(name);
        return unitRepository.save(unit);
    }

    @Override
    public UnitEntity getUnitById(long id) {
        return unitRepository.findOne(id);
    }

    @Override
    public List<IdAndNameDTO> getAllUnitsIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(unitRepository.getAllUnitsIdAndName());
    }

    @Override
    public List<IdAndNameDTO> getTrackUnitsIdAndName(long trackId) {
        return Parser.parseObjectsToIdAndNameEntities(unitRepository.getTrackUnitsIdAndName(trackId));
    }

    @Override
    public boolean checkUnitName(String name) {
        return unitRepository.checkUnitName(name);
    }

}