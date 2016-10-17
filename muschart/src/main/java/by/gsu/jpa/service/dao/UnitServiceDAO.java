package by.gsu.jpa.service.dao;

import java.util.List;

import by.gsu.bean.entity.IdAndNameEntity;
import by.gsu.entity.UnitEntity;

public interface UnitServiceDAO {

    UnitEntity createUnit(String name);

    UnitEntity getUnitById(long id);

    List<IdAndNameEntity> getAllUnitsIdAndName();

    List<IdAndNameEntity> getTrackUnitsIdAndName(long trackId);

    boolean checkUnitName(String name);

}
