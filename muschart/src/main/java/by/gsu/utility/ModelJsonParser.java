package by.gsu.utility;

import static by.gsu.constants.ModelStructureConstants.Models;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.database.dao.ArtistDAO;
import by.gsu.database.dao.GenreDAO;
import by.gsu.database.dao.UnitDAO;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.UnitModel;

public abstract class ModelJsonParser {

    @Autowired
    private static ArtistDAO artistDAO;
    @Autowired
    private static GenreDAO  genreDAO;
    @Autowired
    private static UnitDAO   unitDAO;

    public static List<UnitModel> getUnits(final String json) {
        List<UnitModel> unions = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(Models.UNIT);
            unions.add(getUnitById(id));
        }
        return unions;
    }

    public static List<ArtistModel> getArtists(final String json) {
        List<ArtistModel> artists = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(Models.ARTIST);
            artists.add(getArtistById(id));
        }
        return artists;
    }

    public static List<GenreModel> getGenres(final String json) {
        List<GenreModel> genres = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(Models.GENRE);
            genres.add(getGenreById(id));
        }
        return genres;
    }

    private static UnitModel getUnitById(final long id) {
        return unitDAO.getUnitById(id);
    }

    private static ArtistModel getArtistById(final long id) {
        return artistDAO.getArtistById(id);
    }

    private static GenreModel getGenreById(final long id) {
        return genreDAO.getGenreById(id);
    }

}
