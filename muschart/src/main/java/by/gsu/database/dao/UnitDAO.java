package by.gsu.database.dao;

import java.util.List;

import by.gsu.entity.UnitEntity;

public interface UnitDAO {

    UnitEntity getUnitById(long id);

    List<UnitEntity> getAllUnits();

}
