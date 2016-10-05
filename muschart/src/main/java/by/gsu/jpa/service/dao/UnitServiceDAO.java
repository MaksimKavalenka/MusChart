package by.gsu.jpa.service.dao;

import java.util.Set;

import by.gsu.bean.EntityIdAndName;
import by.gsu.entity.UnitEntity;

public interface UnitServiceDAO {

    UnitEntity getUnitById(long id);

    Set<EntityIdAndName> getAllUnitsIdAndName();

}
