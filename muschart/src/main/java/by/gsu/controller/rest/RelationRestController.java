package by.gsu.controller.rest;

import static by.gsu.constants.ModelStructureConstants.Models;
import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.ARTISTS_PATH;
import static by.gsu.constants.RestConstants.GENRES_PATH;
import static by.gsu.constants.RestConstants.TRACKS_PATH;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.IRelationDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.RelationFactory;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;
import by.gsu.parser.AmplitudeJsonParser;

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
                case Models.TRACK:
                    artists = relationDAO.getTrackArtistsByCriteria(id, sort, order, page);
                    break;
                case Models.GENRE:
                    artists = relationDAO.getGenreArtistsByCriteria(id, sort, order, page);
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
                case Models.TRACK:
                    genres = relationDAO.getTrackGenresByCriteria(id, sort, order, page);
                    break;
                case Models.ARTIST:
                    genres = relationDAO.getArtistGenresByCriteria(id, sort, order, page);
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

    @RequestMapping(value = TRACKS_PATH + "/amplitude/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAmplitudeTracksByCriteria(
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
                default:
                    break;
            }
            if (tracks == null) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<String>(AmplitudeJsonParser.getAmplitudeJson(tracks),
                    HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

}
