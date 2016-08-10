package by.gsu.database.dao;

import by.gsu.exception.ValidationException;
import by.gsu.model.Role;

public interface IRoleDAO extends IDAO {

    Role getRoleById(long id) throws ValidationException;

}
