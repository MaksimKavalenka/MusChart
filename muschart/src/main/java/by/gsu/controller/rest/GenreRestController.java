package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.Rest.GENRES_URL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.constants.EntityConstants.Structure.Entities;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.service.dao.GenreServiceDAO;
import by.gsu.utility.Secure;

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
    public ResponseEntity<List<GenreEntity>> getGenres(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<GenreEntity> genres = genreService.getGenres(sort, order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<GenreEntity>> getEntityGenres(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId, @PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<GenreEntity> genres = null;
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
            return new ResponseEntity<List<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{sort}/{order}/{page}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<GenreEntity>> getUserGenres(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<GenreEntity> genres = genreService.getUserGenres(Secure.getLoggedUser().getId(), sort,
                order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GenreEntity>>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/id_name" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameEntity>> getAllGenresIdAndName() {
        List<IdAndNameEntity> genresIdAndName = genreService.getAllGenresIdAndName();
        if (genresIdAndName == null) {
            return new ResponseEntity<List<IdAndNameEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameEntity>>(genresIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = "/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getGenresPagesCount() {
        int pagesCount = genreService.getGenresPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/pages_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityGenresPagesCount(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        int pagesCount = 0;
        switch (entity) {
            case Entities.ARTIST:
                pagesCount = genreService.getArtistGenresPagesCount(entityId);
                break;
            case Entities.TRACK:
                pagesCount = genreService.getTrackGenresPagesCount(entityId);
                break;
            default:
                break;
        }
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserGenresPagesCount(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        int pagesCount = genreService.getUserGenresPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/check_genre_name/{name}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkGenreName(@PathVariable("name") final String name) {
        boolean exists = genreService.checkGenreName(name);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
