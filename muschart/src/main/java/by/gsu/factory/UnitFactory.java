package by.gsu.factory;

import by.gsu.database.dao.IUnitDAO;
import by.gsu.database.editor.UnitDatabaseEditor;
import by.gsu.exception.ValidationException;

public abstract class UnitFactory {

    public static IUnitDAO getEditor() throws ValidationException {
        return new UnitDatabaseEditor();
    }

}
