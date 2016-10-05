package by.gsu.jpa.service.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.bean.EntityIdAndName;
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
    public Set<EntityIdAndName> getAllUnitsIdAndName() {
        List<Object[]> objectsArray = repository.getAllUnitsIdAndName();
        Set<EntityIdAndName> entitiesIdAndName = new HashSet<>(objectsArray.size());
        for (Object[] object : objectsArray) {
            EntityIdAndName unitBody = new EntityIdAndName((Long) object[0], (String) object[1]);
            entitiesIdAndName.add(unitBody);
        }
        return entitiesIdAndName;
    }

}
