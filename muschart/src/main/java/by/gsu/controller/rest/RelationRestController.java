package by.gsu.controller.rest;

import static by.gsu.constants.ModelStructureConstants.Models;
import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.ARTISTS_PATH;
import static by.gsu.constants.RestConstants.GENRES_PATH;
import static by.gsu.constants.RestConstants.TRACKS_PATH;
import static by.gsu.constants.RestConstants.USERS_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.ArtistDAO;
import by.gsu.database.dao.GenreDAO;
import by.gsu.database.dao.RelationDAO;
import by.gsu.database.dao.TrackDAO;
import by.gsu.database.dao.UserDAO;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.TrackModel;
import by.gsu.model.UserModel;

@RestController
public class RelationRestController {

    @Autowired
    private RelationDAO relationDAO;
    @Autowired
    private ArtistDAO   artistDAO;
    @Autowired
    private GenreDAO    genreDAO;
    @Autowired
    private TrackDAO    trackDAO;
    @Autowired
    private UserDAO     userDAO;

    @RequestMapping(value = ARTISTS_PATH + "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistModel>> getArtistsByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<ArtistModel> artists = null;
        switch (relation) {
            case Models.GENRE:
                artists = relationDAO.getGenreArtistsByCriteria(id, sort, order, page);
                break;
            case Models.TRACK:
                artists = relationDAO.getTrackArtistsByCriteria(id, sort, order, page);
                break;
            case Models.USER:
                artists = relationDAO.getUserArtistsByCriteria(id, sort, order, page);
                break;
            default:
                break;
        }
        if (artists == null) {
            return new ResponseEntity<List<ArtistModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistModel>>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = GENRES_PATH + "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreModel>> getGenresByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<GenreModel> genres = null;
        switch (relation) {
            case Models.ARTIST:
                genres = relationDAO.getArtistGenresByCriteria(id, sort, order, page);
                break;
            case Models.TRACK:
                genres = relationDAO.getTrackGenresByCriteria(id, sort, order, page);
                break;
            case Models.USER:
                genres = relationDAO.getUserGenresByCriteria(id, sort, order, page);
                break;
            default:
                break;
        }
        if (genres == null) {
            return new ResponseEntity<List<GenreModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreModel>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = TRACKS_PATH + "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrackModel>> getTracksByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<TrackModel> tracks = null;
        switch (relation) {
            case Models.ARTIST:
                tracks = relationDAO.getArtistTracksByCriteria(id, sort, order, page);
                break;
            case Models.GENRE:
                tracks = relationDAO.getGenreTracksByCriteria(id, sort, order, page);
                break;
            case Models.USER:
                tracks = relationDAO.getUserTracksByCriteria(id, sort, order, page);
                break;
            default:
                break;
        }
        if (tracks == null) {
            return new ResponseEntity<List<TrackModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TrackModel>>(tracks, HttpStatus.OK);
    }

    @RequestMapping(value = USERS_PATH + "/{idUser}/{relation}/{id}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> setUserLike(@PathVariable("idUser") final long idUser,
            @PathVariable("relation") final String relation, @PathVariable("id") final long id) {
        UserModel user = userDAO.getUserById(idUser);
        switch (relation) {
            case Models.ARTIST:
                ArtistModel artist = artistDAO.getArtistById(id);
                relationDAO.updateUserArtists(user, artist);
                break;
            case Models.GENRE:
                GenreModel genre = genreDAO.getGenreById(id);
                relationDAO.updateUserGenres(user, genre);
                break;
            case Models.TRACK:
                TrackModel track = trackDAO.getTrackById(id);
                relationDAO.updateUserTracks(user, track);
                break;
            default:
                break;
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
