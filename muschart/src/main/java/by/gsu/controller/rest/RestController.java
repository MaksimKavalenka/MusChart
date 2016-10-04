package by.gsu.controller.rest;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.gsu.constants.ModelStructureConstants.AbstractFields;
import by.gsu.database.dao.ArtistDAO;
import by.gsu.database.dao.GenreDAO;
import by.gsu.database.dao.UnitDAO;
import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.UnitEntity;
import by.gsu.entity.UserEntity;

public abstract class RestController {

    public static final String JSON_EXT = ".json";

    @Autowired
    private ArtistDAO          artistDAO;
    @Autowired
    private GenreDAO           genreDAO;
    @Autowired
    private UnitDAO            unitDAO;

    public UserEntity getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = auth.getPrincipal();
        return (UserEntity) user;
    }

    public List<UnitEntity> getUnits(final String json) {
        List<UnitEntity> unions = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(AbstractFields.ID);
            unions.add(getUnitById(id));
        }
        return unions;
    }

    public List<ArtistEntity> getArtists(final String json) {
        List<ArtistEntity> artists = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(AbstractFields.ID);
            artists.add(getArtistById(id));
        }
        return artists;
    }

    public List<GenreEntity> getGenres(final String json) {
        List<GenreEntity> genres = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(AbstractFields.ID);
            genres.add(getGenreById(id));
        }
        return genres;
    }

    private UnitEntity getUnitById(final long id) {
        return unitDAO.getUnitById(id);
    }

    private ArtistEntity getArtistById(final long id) {
        return artistDAO.getArtistById(id);
    }

    private GenreEntity getGenreById(final long id) {
        return genreDAO.getGenreById(id);
    }

}
