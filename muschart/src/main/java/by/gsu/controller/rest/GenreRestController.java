package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.Rest.JSON_EXT;
import static by.gsu.constants.UrlConstants.Rest.GENRE_URL;
import static by.gsu.constants.UrlConstants.Rest.Operation.CHECK_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.CREATE_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.DELETE_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.GET_OPERATION;
import static by.gsu.constants.UrlConstants.Rest.Operation.USER_OPERATION;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.bean.entity.GenreInfoEntity;
import by.gsu.bean.entity.IdAndNameEntity;
import by.gsu.constants.EntityConstants.Structure.Entities;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.UserEntity;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.service.dao.GenreServiceDAO;
import by.gsu.jpa.service.dao.UserServiceDAO;
import by.gsu.utility.Secure;

@RestController
@RequestMapping(GENRE_URL)
public class GenreRestController {

    @Autowired
    private GenreServiceDAO genreService;
    @Autowired
    private UserServiceDAO  userService;

    @RequestMapping(value = CREATE_OPERATION + "/{name}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<GenreEntity> createArtist(@PathVariable("name") final String name) {
        try {
            GenreEntity genre = genreService.createGenre(name);
            return new ResponseEntity<GenreEntity>(genre, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<GenreEntity>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = DELETE_OPERATION + "/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<GenreEntity> deleteGenreById(@PathVariable("id") final long id) {
        genreService.deleteGenreById(id);
        return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = GET_OPERATION + "/{id}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<GenreEntity> getGenreById(@PathVariable("id") final long id) {
        GenreEntity genre = genreService.getGenreById(id);
        if (genre == null) {
            return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<GenreEntity>(genre, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<GenreInfoEntity>> getGenres(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<GenreEntity> genres = genreService.getGenres(sort, order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreInfoEntity>>(HttpStatus.NO_CONTENT);
        }

        List<GenreInfoEntity> genreInfoEntities = new ArrayList<>(genres.size());
        for (GenreEntity genre : genres) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isGenreLiked(user.getId(), genre.getId());
            genreInfoEntities.add(new GenreInfoEntity(genre, isLiked));
        }
        return new ResponseEntity<List<GenreInfoEntity>>(genreInfoEntities, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{entity}/{entityId}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<GenreInfoEntity>> getEntityGenres(
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
            return new ResponseEntity<List<GenreInfoEntity>>(HttpStatus.NO_CONTENT);
        }

        List<GenreInfoEntity> genreInfoEntities = new ArrayList<>(genres.size());
        for (GenreEntity genre : genres) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isGenreLiked(user.getId(), genre.getId());
            genreInfoEntities.add(new GenreInfoEntity(genre, isLiked));
        }
        return new ResponseEntity<List<GenreInfoEntity>>(genreInfoEntities, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<GenreInfoEntity>> getUserGenres(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<GenreEntity> genres = genreService.getUserGenres(Secure.getLoggedUser().getId(), sort,
                order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreInfoEntity>>(HttpStatus.NO_CONTENT);
        }

        List<GenreInfoEntity> genreInfoEntities = new ArrayList<>(genres.size());
        for (GenreEntity genre : genres) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isGenreLiked(user.getId(), genre.getId());
            genreInfoEntities.add(new GenreInfoEntity(genre, isLiked));
        }
        return new ResponseEntity<List<GenreInfoEntity>>(genreInfoEntities, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/all/id_name" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameEntity>> getAllGenresIdAndName() {
        List<IdAndNameEntity> genresIdAndName = genreService.getAllGenresIdAndName();
        if (genresIdAndName == null) {
            return new ResponseEntity<List<IdAndNameEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameEntity>>(genresIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getGenresPagesCount() {
        int pagesCount = genreService.getGenresPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{entity}/{entityId}/pages_count"
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

    @RequestMapping(value = USER_OPERATION + "/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserGenresPagesCount() {
        int pagesCount = genreService.getUserGenresPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = CHECK_OPERATION + "/genre_name/{name}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkGenreName(@PathVariable("name") final String name) {
        boolean exists = genreService.checkGenreName(name);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
