package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.JSON_EXT;
import static by.gsu.constants.UrlConstants.Rest.ARTISTS_URL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.bean.entity.ArtistInfoEntity;
import by.gsu.bean.entity.IdAndNameEntity;
import by.gsu.constants.EntityConstants.Structure.Entities;
import by.gsu.entity.ArtistEntity;
import by.gsu.jpa.service.dao.ArtistServiceDAO;
import by.gsu.jpa.service.dao.UserServiceDAO;
import by.gsu.utility.Parser;
import by.gsu.utility.Secure;

@RestController
@RequestMapping(ARTISTS_URL)
public class ArtistRestController {

    @Autowired
    private ArtistServiceDAO artistService;
    @Autowired
    private UserServiceDAO   userService;

    @RequestMapping(value = "/create/{name}/{photo}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<ArtistEntity> createArtist(@PathVariable("name") final String name,
            @PathVariable("photo") final String photo,
            @PathVariable("genres") final String genres) {
        ArtistEntity artist = artistService.createArtist(name, photo,
                Parser.getIdsFromJson(genres));
        return new ResponseEntity<ArtistEntity>(artist, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<ArtistEntity> deleteArtistById(@PathVariable("id") final long id) {
        artistService.deleteArtistById(id);
        return new ResponseEntity<ArtistEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<ArtistEntity> getArtistById(@PathVariable("id") final long id) {
        ArtistEntity artist = artistService.getArtistById(id);
        if (artist == null) {
            return new ResponseEntity<ArtistEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ArtistEntity>(artist, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<ArtistInfoEntity>> getArtists(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<ArtistEntity> artists = artistService.getArtists(sort, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistInfoEntity>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistInfoEntity> artistInfoEntities = new ArrayList<>(artists.size());
        for (ArtistEntity artist : artists) {
            boolean isLiked = userService.isArtistLiked(artist.getId());
            artistInfoEntities.add(new ArtistInfoEntity(artist, isLiked));
        }
        return new ResponseEntity<List<ArtistInfoEntity>>(artistInfoEntities, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<ArtistInfoEntity>> getEntityArtists(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId, @PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
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
            return new ResponseEntity<List<ArtistInfoEntity>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistInfoEntity> artistInfoEntities = new ArrayList<>(artists.size());
        for (ArtistEntity artist : artists) {
            boolean isLiked = userService.isArtistLiked(artist.getId());
            artistInfoEntities.add(new ArtistInfoEntity(artist, isLiked));
        }
        return new ResponseEntity<List<ArtistInfoEntity>>(artistInfoEntities, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{sort}/{order}/{page}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<ArtistInfoEntity>> getUserArtists(
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<ArtistEntity> artists = artistService.getUserArtists(Secure.getLoggedUser().getId(),
                sort, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistInfoEntity>>(HttpStatus.NO_CONTENT);
        }

        List<ArtistInfoEntity> artistInfoEntities = new ArrayList<>(artists.size());
        for (ArtistEntity artist : artists) {
            boolean isLiked = userService.isArtistLiked(artist.getId());
            artistInfoEntities.add(new ArtistInfoEntity(artist, isLiked));
        }
        return new ResponseEntity<List<ArtistInfoEntity>>(artistInfoEntities, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/id_name" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameEntity>> getAllGenresIdAndName() {
        List<IdAndNameEntity> artistsIdAndName = artistService.getAllArtistsIdAndName();
        if (artistsIdAndName == null) {
            return new ResponseEntity<List<IdAndNameEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameEntity>>(artistsIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = "/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getArtistsPagesCount() {
        int pagesCount = artistService.getArtistsPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/pages_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityArtistsPagesCount(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
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

    @RequestMapping(value = "/user/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserArtistsPagesCount() {
        int pagesCount = artistService.getUserArtistsPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

}
