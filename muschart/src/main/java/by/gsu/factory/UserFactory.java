package by.gsu.factory;

import by.gsu.database.dao.IUserDAO;
import by.gsu.database.editor.UserDatabaseEditor;
import by.gsu.exception.ValidationException;

public abstract class UserFactory {

    public static IUserDAO getEditor() throws ValidationException {
        return new UserDatabaseEditor();
    }

}
