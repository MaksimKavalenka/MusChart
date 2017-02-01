package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.ARTISTS_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.USER_OPERATION;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muschart.constants.EntityConstants.Structure.Entities;
import com.muschart.dto.IdAndNameDTO;
import com.muschart.dto.input.ArtistInputDTO;
import com.muschart.dto.output.ArtistOutputDTO;
import com.muschart.entity.ArtistEntity;
import com.muschart.entity.UserEntity;
import com.muschart.exception.UploadException;
import com.muschart.service.dao.ArtistServiceDAO;
import com.muschart.service.dao.UserServiceDAO;
import com.muschart.solr.service.dao.ArtistSolrServiceDAO;
import com.muschart.utility.Secure;

@RestController
@RequestMapping(ARTISTS_URL)
public class ArtistRestController {

    @Autowired
    private ArtistServiceDAO     artistService;

    @Autowired
    private ArtistSolrServiceDAO artistSolrService;

    @Autowired
    private UserServiceDAO       userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ArtistEntity> createArtist(@RequestBody @Valid ArtistInputDTO artistInput) {
        try {
            ArtistEntity artist = artistService.createArtist(artistInput.getName(), artistInput.getPhoto(), artistInput.getGenresId());
            artistSolrService.createArtist(artist.getId(), artist.getName());
            return new ResponseEntity<ArtistEntity>(artist, HttpStatus.CREATED);
        } catch (UploadException e) {
            return new ResponseEntity<ArtistEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteArtistById(@PathVariable("id") long id) {
        artistService.deleteArtistById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ArtistEntity> getArtistById(@PathVariable("id") long id) {
        ArtistEntity artist = artistService.getArtistById(id);
        if (artist == null) {
            return new ResponseEntity<ArtistEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ArtistEntity>(artist, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<ArtistOutputDTO>> getArtists(@PathVariable("sort") int sort, @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<ArtistEntity> artists = artistService.getArtists(sort, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistOutputDTO>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistOutputDTO> artistsOutput = parseArtistEntityToArtistOutputDTO(artists);
        return new ResponseEntity<List<ArtistOutputDTO>>(artistsOutput, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<ArtistOutputDTO>> getEntityArtists(@PathVariable("entity") String entity, @PathVariable("entityId") long entityId, @PathVariable("sort") int sort, @PathVariable("order") boolean order, @PathVariable("page") int page) {
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
            return new ResponseEntity<List<ArtistOutputDTO>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistOutputDTO> artistsOutput = parseArtistEntityToArtistOutputDTO(artists);
        return new ResponseEntity<List<ArtistOutputDTO>>(artistsOutput, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/{sort}/{order}/{page}", method = RequestMethod.GET)
    public ResponseEntity<List<ArtistOutputDTO>> getUserArtists(@PathVariable("sort") int sort, @PathVariable("order") boolean order, @PathVariable("page") int page) {
        List<ArtistEntity> artists = artistService.getUserArtists(Secure.getLoggedUser().getId(), sort, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistOutputDTO>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistOutputDTO> artistsOutput = parseArtistEntityToArtistOutputDTO(artists);
        return new ResponseEntity<List<ArtistOutputDTO>>(artistsOutput, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/id_name", method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameDTO>> getAllGenresIdAndName() {
        List<IdAndNameDTO> artistsIdAndName = artistService.getAllArtistsIdAndName();
        if (artistsIdAndName == null) {
            return new ResponseEntity<List<IdAndNameDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameDTO>>(artistsIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = "/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getArtistsPagesCount() {
        int pagesCount = artistService.getArtistsPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/pages_count", method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityArtistsPagesCount(@PathVariable("entity") String entity, @PathVariable("entityId") long entityId) {
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

    private List<ArtistOutputDTO> parseArtistEntityToArtistOutputDTO(List<ArtistEntity> artists) {
        List<ArtistOutputDTO> artistsOutput = new ArrayList<>(artists.size());
        for (ArtistEntity artist : artists) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false : userService.isArtistLiked(user.getId(), artist.getId());
            artistsOutput.add(new ArtistOutputDTO(artist, isLiked));
        }
        return artistsOutput;
    }

}