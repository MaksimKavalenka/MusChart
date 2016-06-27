package by.gsu.factory;

import by.gsu.database.dao.IGenreDAO;
import by.gsu.database.editor.GenreDatabaseEditor;
import by.gsu.exception.ValidationException;

public abstract class GenreFactory {

    public static IGenreDAO getEditor() throws ValidationException {
        return new GenreDatabaseEditor();
    }

}
