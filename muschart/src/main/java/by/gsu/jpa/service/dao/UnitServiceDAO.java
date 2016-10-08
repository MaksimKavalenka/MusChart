package by.gsu.jpa.service.dao;

import java.util.List;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.entity.UnitEntity;

public interface UnitServiceDAO {

    UnitEntity getUnitById(long id);

    List<IdAndNameEntity> getAllUnitsIdAndName();

}
