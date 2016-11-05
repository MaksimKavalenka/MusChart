package com.muschart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.UnitEntity;
import com.muschart.jpa.repository.UnitRepository;
import com.muschart.service.dao.UnitServiceDAO;
import com.muschart.utility.Parser;

public class UnitService implements UnitServiceDAO {

    @Autowired
    private UnitRepository repository;

    @Override
    public UnitEntity createUnit(final String name) {
        UnitEntity unit = new UnitEntity(name);
        return repository.save(unit);
    }

    @Override
    public UnitEntity getUnitById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public List<IdAndNameDTO> getAllUnitsIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(repository.getAllUnitsIdAndName());
    }

    @Override
    public List<IdAndNameDTO> getTrackUnitsIdAndName(final long trackId) {
        return Parser.parseObjectsToIdAndNameEntities(repository.getTrackUnitsIdAndName(trackId));
    }

    @Override
    public boolean checkUnitName(final String name) {
        return repository.checkUnitName(name);
    }

}
