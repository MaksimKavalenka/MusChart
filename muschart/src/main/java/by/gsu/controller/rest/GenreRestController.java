package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.Rest.GENRES_URL;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.bean.EntityIdAndName;
import by.gsu.constants.CountElementsConstants;
import by.gsu.constants.EntityConstants.Structure.Entities;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.service.dao.GenreServiceDAO;

@RestController
@RequestMapping(GENRES_URL)
public class GenreRestController extends by.gsu.controller.rest.RestController {

    @Autowired
    private GenreServiceDAO genreService;

    @RequestMapping(value = "/create/{name}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<GenreEntity> createArtist(@PathVariable("name") final String name) {
        try {
            GenreEntity genre = genreService.createGenre(name);
            return new ResponseEntity<GenreEntity>(genre, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<GenreEntity>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<GenreEntity> deleteGenreById(@PathVariable("id") final long id) {
        genreService.deleteGenreById(id);
        return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<GenreEntity> getGenreById(@PathVariable("id") final long id) {
        GenreEntity genre = genreService.getGenreById(id);
        if (genre == null) {
            return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<GenreEntity>(genre, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Set<GenreEntity>> getGenres(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        Set<GenreEntity> genres = genreService.getGenres(sort, order, page);
        if (genres == null) {
            return new ResponseEntity<Set<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Set<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Set<GenreEntity>> getEntityGenres(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId, @PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        Set<GenreEntity> genres = null;
        switch (entity) {
            case Entities.ARTIST:
                genres = genreService.getArtistGenres(entityId, sort, order, page);
                break;
            case Entities.TRACK:
                genres = genreService.getTrackGenres(entityId, sort, order, page);
                break;
            default:
                break;
        }
        if (genres == null) {
            return new ResponseEntity<Set<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Set<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{sort}/{order}/{page}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Set<GenreEntity>> getUserGenres(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        Set<GenreEntity> genres = genreService.getUserGenres(getLoggedUser().getId(), sort, order,
                page);
        if (genres == null) {
            return new ResponseEntity<Set<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Set<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/id/name" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Set<EntityIdAndName>> getAllGenresIdAndName() {
        Set<EntityIdAndName> genresIdAndName = genreService.getAllGenresIdAndName();
        if (genresIdAndName == null) {
            return new ResponseEntity<Set<EntityIdAndName>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Set<EntityIdAndName>>(genresIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = "/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getPageCount() {
        long count = genreService.getGenresCount();
        int pageCount = (int) Math.ceil(count
                / (double) CountElementsConstants.GenreCountElements.GENRE_FULL_COUNT_ELEMENTS);
        return new ResponseEntity<Integer>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/page_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityGenresPageCount(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        long count = 0;
        switch (entity) {
            case Entities.ARTIST:
                count = genreService.getArtistGenresCount(entityId);
                break;
            case Entities.TRACK:
                count = genreService.getTrackGenresCount(entityId);
                break;
            default:
                break;
        }
        int pageCount = (int) Math.ceil(count
                / (double) CountElementsConstants.GenreCountElements.GENRE_FULL_COUNT_ELEMENTS);
        return new ResponseEntity<Integer>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserGenresPageCount(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        long count = genreService.getUserGenresCount(getLoggedUser().getId());
        int pageCount = (int) Math.ceil(count
                / (double) CountElementsConstants.GenreCountElements.GENRE_FULL_COUNT_ELEMENTS);
        return new ResponseEntity<Integer>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/check_genre_name/{name}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkGenreName(@PathVariable("name") final String name) {
        boolean exists = genreService.checkGenreName(name);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
