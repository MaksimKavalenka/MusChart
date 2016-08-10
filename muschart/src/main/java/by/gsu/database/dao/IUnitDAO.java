package by.gsu.database.dao;

import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Unit;

public interface IUnitDAO extends IDAO {

    Unit getUnitById(long id) throws ValidationException;

    List<Unit> getAllUnits() throws ValidationException;

}
