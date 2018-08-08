package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.GENRES_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.CHECK_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.USER_OPERATION;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.constants.EntityConstants.Structure.Entities;
import com.muschart.dto.IdAndNameDTO;
import com.muschart.dto.input.GenreInputDTO;
import com.muschart.dto.output.GenreOutputDTO;
import com.muschart.entity.GenreEntity;
import com.muschart.entity.UserEntity;
import com.muschart.exception.UploadException;
import com.muschart.exception.ValidationException;
import com.muschart.utility.Secure;

@RestController
@RequestMapping(GENRES_URL)
public class GenreRestController extends com.muschart.controller.rest.RestController {

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<GenreEntity> createGenre(@RequestBody @Valid GenreInputDTO genreInput) {
        try {
            GenreEntity genre = genreDatabaseService.createGenre(genreInput.getName());
            genreSolrService.createGenre(genre.getId(), genre.getName());
            return new ResponseEntity<GenreEntity>(genre, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<GenreEntity>(HttpStatus.CONFLICT);
        } catch (UploadException e) {
            return new ResponseEntity<GenreEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGenreById(@PathVariable("id") long id) {
        genreDatabaseService.deleteGenreById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<GenreEntity> getGenreById(@PathVariable("id") long id) {
        GenreEntity genre = genreDatabaseService.getGenreById(id);
        if (genre == null) {
            return new ResponseEntity<GenreEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<GenreEntity>(genre, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<GenreOutputDTO>> getGenres(@PathVariable("sort") int sort, @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<GenreEntity> genres = genreDatabaseService.getGenres(sort, order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreOutputDTO>>(HttpStatus.NO_CONTENT);
        }

        List<GenreOutputDTO> genresOutput = parseGenreEntityToGenreOutputDTO(genres);
        return new ResponseEntity<List<GenreOutputDTO>>(genresOutput, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<GenreOutputDTO>> getEntityGenres(@PathVariable("entity") String entity, @PathVariable("entityId") long entityId, @PathVariable("sort") int sort, @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<GenreEntity> genres = null;
        switch (entity) {
            case Entities.ARTIST:
                genres = genreDatabaseService.getArtistGenres(entityId, sort, order, page);
                break;
            case Entities.TRACK:
                genres = genreDatabaseService.getTrackGenres(entityId, sort, order, page);
                break;
            default:
                break;
        }
        if (genres == null) {
            return new ResponseEntity<List<GenreOutputDTO>>(HttpStatus.NO_CONTENT);
        }

        List<GenreOutputDTO> genresOutput = parseGenreEntityToGenreOutputDTO(genres);
        return new ResponseEntity<List<GenreOutputDTO>>(genresOutput, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<GenreOutputDTO>> getUserGenres(@PathVariable("sort") int sort, @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<GenreEntity> genres = genreDatabaseService.getUserGenres(Secure.getLoggedUser().getId(), sort, order, page);
        if (genres == null) {
            return new ResponseEntity<List<GenreOutputDTO>>(HttpStatus.NO_CONTENT);
        }

        List<GenreOutputDTO> genresOutput = parseGenreEntityToGenreOutputDTO(genres);
        return new ResponseEntity<List<GenreOutputDTO>>(genresOutput, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/id_name", method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameDTO>> getAllGenresIdAndName() {
        List<IdAndNameDTO> genresIdAndName = genreDatabaseService.getAllGenresIdAndName();
        if (genresIdAndName == null) {
            return new ResponseEntity<List<IdAndNameDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameDTO>>(genresIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = "/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getGenresPagesCount() {
        int pagesCount = genreDatabaseService.getGenresPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityGenresPagesCount(@PathVariable("entity") String entity, @PathVariable("entityId") long entityId) {
        int pagesCount = 0;
        switch (entity) {
            case Entities.ARTIST:
                pagesCount = genreDatabaseService.getArtistGenresPagesCount(entityId);
                break;
            case Entities.TRACK:
                pagesCount = genreDatabaseService.getTrackGenresPagesCount(entityId);
                break;
            default:
                break;
        }
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserGenresPagesCount() {
        int pagesCount = genreDatabaseService.getUserGenresPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = CHECK_OPERATION + "/genre_name", method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkGenreName(@RequestBody @Valid String name) {
        boolean exists = genreDatabaseService.checkGenreName(name);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

    private List<GenreOutputDTO> parseGenreEntityToGenreOutputDTO(List<GenreEntity> genres) {
        List<GenreOutputDTO> genresOutput = new ArrayList<>(genres.size());
        for (GenreEntity genre : genres) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false : userDatabaseService.isGenreLiked(user.getId(), genre.getId());
            genresOutput.add(new GenreOutputDTO(genre, isLiked));
        }
        return genresOutput;
    }

}