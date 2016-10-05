package by.gsu.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.gsu.entity.UnitEntity;

public interface UnitRepository extends CrudRepository<UnitEntity, Long> {

    @Query("SELECT unit.id, unit.name FROM UnitEntity unit")
    List<Object[]> getAllUnitsIdAndName();

}
