package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.ARTISTS_PATH;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.IArtistDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.model.Artist;
import by.gsu.parser.ModelJsonParser;

@RestController
public class ArtistRestController {

    @RequestMapping(value = ARTISTS_PATH + "/create/{name}/{photo}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> createArtist(@PathVariable("name") final String name,
            @PathVariable("photo") final String photo,
            @PathVariable("genres") final String genres) {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            artistDAO.createArtist(name, photo, ModelJsonParser.getGenres(genres));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = ARTISTS_PATH + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artist>> getArtistsByCriteria(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            List<Artist> artists = artistDAO.getArtistsByCriteria(sort, order, page);
            if (artists == null) {
                return new ResponseEntity<List<Artist>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Artist>>(artists, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Artist>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = ARTISTS_PATH
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artist>> getAllArtists() {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            List<Artist> artists = artistDAO.getAllArtists();
            if (artists == null) {
                return new ResponseEntity<List<Artist>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Artist>>(artists, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Artist>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = ARTISTS_PATH + "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<Artist> deleteArtistById(@PathVariable("id") final long id) {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            artistDAO.deleteArtistById(id);
            return new ResponseEntity<Artist>(HttpStatus.NO_CONTENT);
        } catch (ValidationException e) {
            return new ResponseEntity<Artist>(HttpStatus.CONFLICT);
        }
    }

}
