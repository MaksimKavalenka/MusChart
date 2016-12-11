package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.GENRES_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.CHECK_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.CREATE_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.DELETE_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.GET_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.USER_OPERATION;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.constants.EntityConstants.Structure.Entities;
import com.muschart.dto.GenreDTO;
import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.GenreEntity;
import com.muschart.entity.UserEntity;
import com.muschart.exception.ValidationException;
import com.muschart.service.dao.GenreServiceDAO;
import com.muschart.service.dao.UserServiceDAO;
import com.muschart.utility.Secure;

@RestController
@RequestMapping(GENRES_URL)
public class GenreRestController {

    @Autowired
    private GenreServiceDAO genreService;
    @Autowired
    private UserServiceDAO  userService;

    @RequestMapping(value = CREATE_OPERATION + "/{name}", method = RequestMethod.POST)
    public ResponseEntity<GenreEntity> createArtist(@PathVariable("name") String name) {
        try {
            GenreEntity genre = genreService.createGenre(name);
            return new ResponseEntity<GenreEntity>(genre, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<GenreEntity>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = DELETE_OPERATION + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<GenreEntity> deleteGenreById(@PathVariable("id") long id) {
        genreService.deleteGenreById(id);
        return new ResponseEntity<GenreEntity>(HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<GenreEntity> getGenreById(@PathVariable("id") long id) {
        GenreEntity genre = genreService.getGenreById(id);
        if (genre == null) {
            return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<GenreEntity>(genre, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<GenreDTO>> getGenres(@PathVariable("sort") int sort,
            @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<GenreEntity> genres = genreService.getGenres(sort, order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreDTO>>(HttpStatus.NO_CONTENT);
        }

        List<GenreDTO> genresDto = new ArrayList<>(genres.size());
        for (GenreEntity genre : genres) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isGenreLiked(user.getId(), genre.getId());
            genresDto.add(new GenreDTO(genre, isLiked));
        }
        return new ResponseEntity<List<GenreDTO>>(genresDto, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION
            + "/{entity}/{entityId}/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<GenreDTO>> getEntityGenres(@PathVariable("entity") String entity,
            @PathVariable("entityId") long entityId, @PathVariable("sort") int sort,
            @PathVariable("order") boolean order, @PathVariable("page") int page) {
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
            return new ResponseEntity<List<GenreDTO>>(HttpStatus.NO_CONTENT);
        }

        List<GenreDTO> genresDto = new ArrayList<>(genres.size());
        for (GenreEntity genre : genres) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isGenreLiked(user.getId(), genre.getId());
            genresDto.add(new GenreDTO(genre, isLiked));
        }
        return new ResponseEntity<List<GenreDTO>>(genresDto, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<GenreDTO>> getUserGenres(@PathVariable("sort") int sort,
            @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<GenreEntity> genres = genreService.getUserGenres(Secure.getLoggedUser().getId(), sort,
                order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreDTO>>(HttpStatus.NO_CONTENT);
        }

        List<GenreDTO> genresDto = new ArrayList<>(genres.size());
        for (GenreEntity genre : genres) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isGenreLiked(user.getId(), genre.getId());
            genresDto.add(new GenreDTO(genre, isLiked));
        }
        return new ResponseEntity<List<GenreDTO>>(genresDto, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/all/id_name", method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameDTO>> getAllGenresIdAndName() {
        List<IdAndNameDTO> genresIdAndName = genreService.getAllGenresIdAndName();
        if (genresIdAndName == null) {
            return new ResponseEntity<List<IdAndNameDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameDTO>>(genresIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getGenresPagesCount() {
        int pagesCount = genreService.getGenresPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION
            + "/{entity}/{entityId}/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityGenresPagesCount(@PathVariable("entity") String entity,
            @PathVariable("entityId") long entityId) {
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

    @RequestMapping(value = USER_OPERATION + "/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserGenresPagesCount() {
        int pagesCount = genreService.getUserGenresPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = CHECK_OPERATION + "/genre_name/{name}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkGenreName(@PathVariable("name") String name) {
        boolean exists = genreService.checkGenreName(name);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}