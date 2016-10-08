package by.gsu.jpa.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.entity.UnitEntity;
import by.gsu.jpa.repository.UnitRepository;
import by.gsu.jpa.service.dao.UnitServiceDAO;

public class UnitService implements UnitServiceDAO {

    @Autowired
    private UnitRepository repository;

    @Override
    public UnitEntity getUnitById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public List<IdAndNameEntity> getAllUnitsIdAndName() {
        List<Object[]> objectsArray = repository.getAllUnitsIdAndName();
        List<IdAndNameEntity> idAndNameEntities = new ArrayList<>(objectsArray.size());
        for (Object[] object : objectsArray) {
            IdAndNameEntity unitIdAndName = new IdAndNameEntity((Long) object[0],
                    (String) object[1]);
            idAndNameEntities.add(unitIdAndName);
        }
        return idAndNameEntities;
    }

}
