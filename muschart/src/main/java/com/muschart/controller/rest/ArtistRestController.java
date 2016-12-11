package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.ARTISTS_URL;
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
import com.muschart.dto.ArtistDTO;
import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.ArtistEntity;
import com.muschart.entity.UserEntity;
import com.muschart.service.dao.ArtistServiceDAO;
import com.muschart.service.dao.UserServiceDAO;
import com.muschart.utility.Parser;
import com.muschart.utility.Secure;

@RestController
@RequestMapping(ARTISTS_URL)
public class ArtistRestController {

    @Autowired
    private ArtistServiceDAO artistService;
    @Autowired
    private UserServiceDAO   userService;

    @RequestMapping(value = CREATE_OPERATION
            + "/{name}/{photo}/{genres}", method = RequestMethod.POST)
    public ResponseEntity<ArtistEntity> createArtist(@PathVariable("name") String name,
            @PathVariable("photo") String photo, @PathVariable("genres") String genres) {
        ArtistEntity artist = artistService.createArtist(name, photo,
                Parser.getIdsFromJson(genres));
        return new ResponseEntity<ArtistEntity>(artist, HttpStatus.CREATED);
    }

    @RequestMapping(value = DELETE_OPERATION + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ArtistEntity> deleteArtistById(@PathVariable("id") long id) {
        artistService.deleteArtistById(id);
        return new ResponseEntity<ArtistEntity>(HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ArtistEntity> getArtistById(@PathVariable("id") long id) {
        ArtistEntity artist = artistService.getArtistById(id);
        if (artist == null) {
            return new ResponseEntity<ArtistEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ArtistEntity>(artist, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<ArtistDTO>> getArtists(@PathVariable("sort") int sort,
            @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<ArtistEntity> artists = artistService.getArtists(sort, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistDTO>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistDTO> artistsDto = new ArrayList<>(artists.size());
        for (ArtistEntity artist : artists) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isArtistLiked(user.getId(), artist.getId());
            artistsDto.add(new ArtistDTO(artist, isLiked));
        }
        return new ResponseEntity<List<ArtistDTO>>(artistsDto, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION
            + "/{entity}/{entityId}/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<ArtistDTO>> getEntityArtists(@PathVariable("entity") String entity,
            @PathVariable("entityId") long entityId, @PathVariable("sort") int sort,
            @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<ArtistEntity> artists = null;
        switch (entity) {
            case Entities.GENRE:
                artists = artistService.getGenreArtists(entityId, sort, order, page);
                break;
            case Entities.TRACK:
                artists = artistService.getTrackArtists(entityId, sort, order, page);
                break;
            default:
                break;
        }
        if (artists == null) {
            return new ResponseEntity<List<ArtistDTO>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistDTO> artistsDto = new ArrayList<>(artists.size());
        for (ArtistEntity artist : artists) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isArtistLiked(user.getId(), artist.getId());
            artistsDto.add(new ArtistDTO(artist, isLiked));
        }
        return new ResponseEntity<List<ArtistDTO>>(artistsDto, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<ArtistDTO>> getUserArtists(@PathVariable("sort") int sort,
            @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<ArtistEntity> artists = artistService.getUserArtists(Secure.getLoggedUser().getId(),
                sort, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistDTO>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistDTO> artistsDto = new ArrayList<>(artists.size());
        for (ArtistEntity artist : artists) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isArtistLiked(user.getId(), artist.getId());
            artistsDto.add(new ArtistDTO(artist, isLiked));
        }
        return new ResponseEntity<List<ArtistDTO>>(artistsDto, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/all/id_name", method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameDTO>> getAllGenresIdAndName() {
        List<IdAndNameDTO> artistsIdAndName = artistService.getAllArtistsIdAndName();
        if (artistsIdAndName == null) {
            return new ResponseEntity<List<IdAndNameDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameDTO>>(artistsIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getArtistsPagesCount() {
        int pagesCount = artistService.getArtistsPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION
            + "/{entity}/{entityId}/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityArtistsPagesCount(@PathVariable("entity") String entity,
            @PathVariable("entityId") long entityId) {
        int pagesCount = 0;
        switch (entity) {
            case Entities.GENRE:
                pagesCount = artistService.getGenreArtistsPagesCount(entityId);
                break;
            case Entities.TRACK:
                pagesCount = artistService.getTrackArtistsPagesCount(entityId);
                break;
            default:
                break;
        }
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserArtistsPagesCount() {
        int pagesCount = artistService.getUserArtistsPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

}