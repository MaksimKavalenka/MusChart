package by.gsu.controller.rest;

import static by.gsu.constants.ModelStructureConstants.Models;
import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.ARTISTS_PATH;
import static by.gsu.constants.RestConstants.GENRES_PATH;
import static by.gsu.constants.RestConstants.TRACKS_PATH;
import static by.gsu.constants.RestConstants.USERS_PATH;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.dao.IGenreDAO;
import by.gsu.database.dao.IRelationDAO;
import by.gsu.database.dao.ITrackDAO;
import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.factory.GenreFactory;
import by.gsu.factory.RelationFactory;
import by.gsu.factory.TrackFactory;
import by.gsu.factory.UserFactory;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;
import by.gsu.model.User;

@RestController
public class RelationRestController {

    @RequestMapping(value = ARTISTS_PATH + "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artist>> getArtistsByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        try (IRelationDAO relationDAO = RelationFactory.getEditor()) {
            List<Artist> artists = null;
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
                return new ResponseEntity<List<Artist>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Artist>>(artists, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Artist>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = GENRES_PATH + "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Genre>> getGenresByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        try (IRelationDAO relationDAO = RelationFactory.getEditor()) {
            List<Genre> genres = null;
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
                return new ResponseEntity<List<Genre>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Genre>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Track>> getTracksByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        try (IRelationDAO relationDAO = RelationFactory.getEditor()) {
            List<Track> tracks = null;
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
                return new ResponseEntity<List<Track>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Track>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = USERS_PATH + "/{idUser}/{relation}/{id}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> setUserLike(@PathVariable("idUser") final long idUser,
            @PathVariable("relation") final String relation, @PathVariable("id") final long id) {
        try (IRelationDAO relationDAO = RelationFactory.getEditor()) {
            User user;
            try (IUserDAO userDAO = UserFactory.getEditor()) {
                user = userDAO.getUserById(idUser);
            }
            switch (relation) {
                case Models.ARTIST:
                    try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
                        Artist artist = artistDAO.getArtistById(id);
                        relationDAO.updateUserArtists(user, artist);
                    }
                    break;
                case Models.GENRE:
                    try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
                        Genre genre = genreDAO.getGenreById(id);
                        relationDAO.updateUserGenres(user, genre);
                    }
                    break;
                case Models.TRACK:
                    try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
                        Track track = trackDAO.getTrackById(id);
                        relationDAO.updateUserTracks(user, track);
                    }
                    break;
                default:
                    break;
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

}
