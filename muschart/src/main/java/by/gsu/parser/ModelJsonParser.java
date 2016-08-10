package by.gsu.parser;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import by.gsu.constants.StructureConstants;
import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.dao.IGenreDAO;
import by.gsu.database.dao.IUnitDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.factory.GenreFactory;
import by.gsu.factory.UnitFactory;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;
import by.gsu.model.Unit;

public abstract class ModelJsonParser {

    public static String getArtistsName(final Track track) throws ValidationException {
        String artistsName = track.getArtists().get(0).getName();
        for (int i = 1; i < track.getArtists().size(); i++) {
            artistsName += track.getUnits().get(i - 1).getName();
            artistsName += track.getArtists().get(i).getName();
        }
        return artistsName;
    }

    public static List<Unit> getUnits(final String json) throws ValidationException {
        List<Unit> unions = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(StructureConstants.RelationColumns.ID_UNIT);
            unions.add(getUnitById(id));
        }
        return unions;
    }

    public static List<Artist> getArtists(final String json) throws ValidationException {
        List<Artist> artists = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(StructureConstants.RelationColumns.ID_ARTIST);
            artists.add(getArtistById(id));
        }
        return artists;
    }

    public static List<Genre> getGenres(final String json) throws ValidationException {
        List<Genre> genres = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(StructureConstants.RelationColumns.ID_GENRE);
            genres.add(getGenreById(id));
        }
        return genres;
    }

    private static Unit getUnitById(final long id) throws ValidationException {
        try (IUnitDAO unionDAO = UnitFactory.getEditor()) {
            return unionDAO.getUnitById(id);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private static Artist getArtistById(final long id) throws ValidationException {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            return artistDAO.getArtistById(id);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private static Genre getGenreById(final long id) throws ValidationException {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            return genreDAO.getGenreById(id);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
