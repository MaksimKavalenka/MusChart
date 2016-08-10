package by.gsu.factory;

import by.gsu.database.dao.IRoleDAO;
import by.gsu.database.editor.RoleDatabaseEditor;
import by.gsu.exception.ValidationException;

public abstract class RoleFactory {

    public static IRoleDAO getEditor() throws ValidationException {
        return new RoleDatabaseEditor();
    }

}
