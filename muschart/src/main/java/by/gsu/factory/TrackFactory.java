package by.gsu.factory;

import by.gsu.database.dao.ITrackDAO;
import by.gsu.database.editor.TrackDatabaseEditor;
import by.gsu.exception.ValidationException;

public abstract class TrackFactory {

    public static ITrackDAO getEditor() throws ValidationException {
        return new TrackDatabaseEditor();
    }

}
