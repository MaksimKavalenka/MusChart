package com.muschart.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.muschart.entity.UnitEntity;

@Service("unitRepository")
public interface UnitRepository extends CrudRepository<UnitEntity, Long> {

    @Query("SELECT unit.id, unit.name FROM UnitEntity unit ORDER BY unit.id ASC")
    List<Object[]> getAllUnitsIdAndName();

    @Query("SELECT unit.id, unit.name FROM TrackUnitEntity trackUnit JOIN trackUnit.track track JOIN trackUnit.unit unit WHERE track.id = ?1 ORDER BY trackUnit.id")
    List<Object[]> getTrackUnitsIdAndName(long trackId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UnitEntity WHERE name = ?1")
    boolean checkUnitName(String name);

}