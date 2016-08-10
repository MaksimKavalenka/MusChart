package by.gsu.database.editor;

import by.gsu.database.dao.IRoleDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Role;

public class RoleDatabaseEditor extends DatabaseEditor implements IRoleDAO {

    public RoleDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public Role getRoleById(final long id) throws ValidationException {
        return (Role) session.get(Role.class, id);
    }

}
