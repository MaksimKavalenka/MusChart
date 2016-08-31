package by.gsu.database.dao;

import java.util.List;

import by.gsu.model.UnitModel;

public interface UnitDAO {

    UnitModel getUnitById(long id);

    List<UnitModel> getAllUnits();

}
