package by.gsu.factory;

import by.gsu.database.dao.IRelationDAO;
import by.gsu.database.editor.RelationDatabaseEditor;
import by.gsu.exception.ValidationException;

public abstract class RelationFactory {

    public static IRelationDAO getEditor() throws ValidationException {
        return new RelationDatabaseEditor();
    }

}
