package by.gsu.action;

import static by.gsu.constants.DefaultConstants.COUNT_DEFAULT;

import javax.servlet.http.HttpServletRequest;

import by.gsu.constants.ActionConstants;
import by.gsu.constants.PropertyConstants;
import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.dao.IGenreDAO;
import by.gsu.database.dao.ITrackDAO;
import by.gsu.database.structure.Tables;
import by.gsu.exception.IllegalDataException;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.factory.GenreFactory;
import by.gsu.factory.TrackFactory;

public abstract class LoadDataAction {

    public static void load(final HttpServletRequest request)
            throws ValidationException, IllegalDataException {
        ActionConstants action = (ActionConstants) request.getAttribute(PropertyConstants.ACTION);
        switch (action) {
            case TRACKS:
                loadTracks(request);
                break;
            case ARTISTS:
                loadActors(request);
                break;
            case GENRES:
                loadTags(request);
                break;
            default:
                break;
        }
        DataAction.checkError(request);
    }

    private static void loadTracks(final HttpServletRequest request) throws ValidationException {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            int page = (int) request.getAttribute(PropertyConstants.PAGE);
            request.setAttribute(Tables.TRACK.toString(),
                    trackDAO.getTracks((page - 1) * COUNT_DEFAULT + 1, page * COUNT_DEFAULT));
        }
    }

    private static void loadActors(final HttpServletRequest request) throws ValidationException {
        try (IArtistDAO actorDAO = ArtistFactory.getEditor()) {
            int page = (int) request.getAttribute(PropertyConstants.PAGE);
            request.setAttribute(Tables.ARTIST.toString(),
                    actorDAO.getArtists((page - 1) * COUNT_DEFAULT + 1, page * COUNT_DEFAULT));
        }
    }

    private static void loadTags(final HttpServletRequest request) throws ValidationException {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            int page = (int) request.getAttribute(PropertyConstants.PAGE);
            request.setAttribute(Tables.GENRE.toString(),
                    genreDAO.getGenres((page - 1) * COUNT_DEFAULT + 1, page * COUNT_DEFAULT));
        }
    }

}
