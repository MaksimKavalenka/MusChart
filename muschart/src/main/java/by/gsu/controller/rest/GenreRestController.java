package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.GENRES_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.GenreDAO;
import by.gsu.model.GenreModel;

@RestController
public class GenreRestController {

    @Autowired
    private GenreDAO genreDAO;

    @RequestMapping(value = GENRES_PATH + "/create/{name}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> createArtist(@PathVariable("name") final String name) {
        genreDAO.createGenre(name);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = GENRES_PATH + "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<GenreModel> deleteGenreById(@PathVariable("id") final long id) {
        genreDAO.deleteGenreById(id);
        return new ResponseEntity<GenreModel>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = GENRES_PATH + "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreModel> getGenreById(@PathVariable("id") final long id) {
        GenreModel genre = genreDAO.getGenreById(id);
        if (genre == null) {
            return new ResponseEntity<GenreModel>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<GenreModel>(genre, HttpStatus.OK);
    }

    @RequestMapping(value = GENRES_PATH + "/{name}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreModel> getGenreByName(@PathVariable("name") final String name) {
        GenreModel genre = genreDAO.getGenreByName(name);
        if (genre == null) {
            return new ResponseEntity<GenreModel>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<GenreModel>(genre, HttpStatus.OK);
    }

    @RequestMapping(value = GENRES_PATH + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreModel>> getGenresByCriteria(
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<GenreModel> genres = genreDAO.getGenresByCriteria(sort, order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreModel>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = GENRES_PATH
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreModel>> getAllGenres() {
        List<GenreModel> genres = genreDAO.getAllGenres();
        if (genres == null) {
            return new ResponseEntity<List<GenreModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreModel>>(genres, HttpStatus.OK);
    }

}
