package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.Rest.GENRES_URL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.constants.CountElementsConstants;
import by.gsu.database.dao.GenreDAO;
import by.gsu.database.dao.RelationDAO;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;

@RestController
@RequestMapping(GENRES_URL)
public class GenreRestController extends by.gsu.controller.rest.RestController {

    @Autowired
    private GenreDAO    genreDAO;
    @Autowired
    private RelationDAO relationDAO;

    @RequestMapping(value = "/create/{name}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<GenreEntity> createArtist(@PathVariable("name") final String name) {
        try {
            GenreEntity genre = genreDAO.createGenre(name);
            return new ResponseEntity<GenreEntity>(genre, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<GenreEntity>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<GenreEntity> deleteGenreById(@PathVariable("id") final long id) {
        genreDAO.deleteGenreById(id);
        return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreEntity> getGenreById(@PathVariable("id") final long id) {
        GenreEntity genre = genreDAO.getGenreById(id);
        if (genre == null) {
            return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<GenreEntity>(genre, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreEntity>> getGenresByCriteria(
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<GenreEntity> genres = relationDAO.getElementsByCriteria(GenreEntity.class, sort, order,
                page);
        if (genres == null) {
            return new ResponseEntity<List<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreEntity>> getGenresByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<GenreEntity> genres = relationDAO.getElementsByCriteria(GenreEntity.class, sort,
                relation, id, order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/all"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreEntity>> getAllGenres() {
        List<GenreEntity> genres = genreDAO.getAllGenres();
        if (genres == null) {
            return new ResponseEntity<List<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/page_amount"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageAmount() {
        List<GenreEntity> genres = genreDAO.getAllGenres();
        int amount = (int) Math.ceil(genres.size()
                / (double) CountElementsConstants.GenreCountElements.GENRE_FULL_COUNT_ELEMENTS);
        return new ResponseEntity<Integer>(amount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{relation}/{id}/page_amount"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageAmount(@PathVariable("id") final long id,
            @PathVariable("relation") final String relation) {
        int amount = relationDAO.getSizeByCriteria(GenreEntity.class, relation, id);
        return new ResponseEntity<Integer>(amount, HttpStatus.OK);
    }

    @RequestMapping(value = "/check_name/{name}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getGenreByName(@PathVariable("name") final String name) {
        boolean exists = genreDAO.checkName(name);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
