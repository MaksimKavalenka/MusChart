package by.gsu.controller.rest;

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

    @RequestMapping(value = "/genre/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/genre/{idFrom}_{idTo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Genre>> getGenresByIds(@PathVariable("idFrom") final long idFrom,
            @PathVariable("idTo") final long idTo) {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            List<Genre> genres = genreDAO.getGenresByIds(idFrom, idTo);
            if (genres == null) {
                return new ResponseEntity<List<Genre>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Genre>>(genres, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Genre>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/genre/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/genre/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Genre> deleteGenreById(@PathVariable("id") final long id) {
        try (IGenreDAO genreDAO = GenreFactory.getEditor()) {
            genreDAO.deleteGenreById(id);
            return new ResponseEntity<Genre>(HttpStatus.NO_CONTENT);
        } catch (ValidationException e) {
            return new ResponseEntity<Genre>(HttpStatus.CONFLICT);
        }
    }

}
