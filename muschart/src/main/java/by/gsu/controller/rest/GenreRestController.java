package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.GENRES_PATH;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.IGenreDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.GenreFactory;
import by.gsu.model.Genre;

@RestController
public class GenreRestController {

    @RequestMapping(value = GENRES_PATH + "/create/{name}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> createArtist(@PathVariable("name") final String name) {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            genreDAO.createGenre(name);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = GENRES_PATH + "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<Genre> deleteGenreById(@PathVariable("id") final long id) {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            genreDAO.deleteGenreById(id);
            return new ResponseEntity<Genre>(HttpStatus.NO_CONTENT);
        } catch (ValidationException e) {
            return new ResponseEntity<Genre>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = GENRES_PATH + "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") final long id) {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            Genre genre = genreDAO.getGenreById(id);
            if (genre == null) {
                return new ResponseEntity<Genre>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Genre>(genre, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Genre>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = GENRES_PATH + "/{name}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> getGenreByName(@PathVariable("name") final String name) {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            Genre genre = genreDAO.getGenreByName(name);
            if (genre == null) {
                return new ResponseEntity<Genre>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Genre>(genre, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Genre>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = GENRES_PATH + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Genre>> getGenresByCriteria(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            List<Genre> genres = genreDAO.getGenresByCriteria(sort, order, page);
            if (genres == null) {
                return new ResponseEntity<List<Genre>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Genre>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = GENRES_PATH
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Genre>> getAllGenres() {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            List<Genre> genres = genreDAO.getAllGenres();
            if (genres == null) {
                return new ResponseEntity<List<Genre>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Genre>>(HttpStatus.CONFLICT);
        }
    }

}
