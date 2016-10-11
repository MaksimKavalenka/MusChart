package by.gsu.jpa.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.entity.UnitEntity;
import by.gsu.jpa.repository.UnitRepository;
import by.gsu.jpa.service.dao.UnitServiceDAO;
import by.gsu.utility.Parser;

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
    public List<IdAndNameEntity> getAllUnitsIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(repository.getAllUnitsIdAndName());
    }

    @Override
    public List<IdAndNameEntity> getTrackUnitsIdAndName(final long trackId) {
        return Parser.parseObjectsToIdAndNameEntities(repository.getTrackUnitsIdAndName(trackId));
    }

    @Override
    public boolean checkUnitName(final String name) {
        return repository.checkUnitName(name);
    }

}
