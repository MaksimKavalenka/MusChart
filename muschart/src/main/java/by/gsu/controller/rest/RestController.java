package by.gsu.controller.rest;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.constants.ModelStructureConstants.ModelFields;
import by.gsu.database.dao.ArtistDAO;
import by.gsu.database.dao.GenreDAO;
import by.gsu.database.dao.RoleDAO;
import by.gsu.database.dao.TrackDAO;
import by.gsu.database.dao.UnitDAO;
import by.gsu.database.dao.UserDAO;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.UnitModel;

public abstract class RestController {

    @Autowired
    public ArtistDAO artistDAO;
    @Autowired
    public GenreDAO  genreDAO;
    @Autowired
    public RoleDAO   roleDAO;
    @Autowired
    public TrackDAO  trackDAO;
    @Autowired
    public UnitDAO   unitDAO;
    @Autowired
    public UserDAO   userDAO;

    public List<UnitModel> getUnits(final String json) {
        List<UnitModel> unions = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(ModelFields.ID);
            unions.add(getUnitById(id));
        }
        return unions;
    }

    public List<ArtistModel> getArtists(final String json) {
        List<ArtistModel> artists = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(ModelFields.ID);
            artists.add(getArtistById(id));
        }
        return artists;
    }

    public List<GenreModel> getGenres(final String json) {
        List<GenreModel> genres = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(ModelFields.ID);
            genres.add(getGenreById(id));
        }
        return genres;
    }

    private UnitModel getUnitById(final long id) {
        return unitDAO.getUnitById(id);
    }

    private ArtistModel getArtistById(final long id) {
        return artistDAO.getArtistById(id);
    }

    private GenreModel getGenreById(final long id) {
        return genreDAO.getGenreById(id);
    }

}
