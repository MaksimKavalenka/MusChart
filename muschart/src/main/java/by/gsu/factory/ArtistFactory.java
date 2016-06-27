package by.gsu.factory;

import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.editor.ArtistDatabaseEditor;
import by.gsu.exception.ValidationException;

public abstract class ArtistFactory {

    public static IArtistDAO getEditor() throws ValidationException {
        return new ArtistDatabaseEditor();
    }

}
