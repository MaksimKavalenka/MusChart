package by.gsu.parser;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import by.gsu.constants.StructureConstants;
import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.dao.IGenreDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.factory.GenreFactory;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;

public abstract class ModelJsonParser {

    public static String castNameToFullCastName(final Track track) throws ValidationException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            castName += jsonObject.getLong(StructureConstants.TrackColumns.ID);
            castName += jsonObject.getLong(StructureConstants.TrackColumns.CAST_NAME);
        }
        return castName;
    }

    public static String getCastName(final String json) {
        String castName = "";
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            castName += jsonObject.getLong(StructureConstants.TrackColumns.ID);
            castName += jsonObject.getLong(StructureConstants.TrackColumns.CAST_NAME);
        }
        return castName;
    }

    public static List<Artist> getArtists(final String json) throws ValidationException {
        List<Artist> artists = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(StructureConstants.ArtistColumns.ID);
            artists.add(getArtistById(id));
        }
        return artists;
    }

    public static List<Genre> getGenres(final String json) throws ValidationException {
        List<Genre> genres = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(StructureConstants.GenreColumns.ID);
            genres.add(getGenreById(id));
        }
        return genres;
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
