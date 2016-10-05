package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.Rest.ARTISTS_URL;

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
import by.gsu.database.dao.ArtistDAO;
import by.gsu.database.dao.RelationDAO;
import by.gsu.entity.ArtistEntity;

@RestController
@RequestMapping(ARTISTS_URL)
public class ArtistRestController extends by.gsu.controller.rest.RestController {

    @Autowired
    private ArtistDAO   artistDAO;
    @Autowired
    private RelationDAO relationDAO;

    @RequestMapping(value = "/create/{name}/{photo}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<ArtistEntity> createArtist(@PathVariable("name") final String name,
            @PathVariable("photo") final String photo,
            @PathVariable("genres") final String genres) {
        ArtistEntity artist = artistDAO.createArtist(name, photo, getGenres(genres));
        return new ResponseEntity<ArtistEntity>(artist, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<ArtistEntity> deleteArtistById(@PathVariable("id") final long id) {
        artistDAO.deleteArtistById(id);
        return new ResponseEntity<ArtistEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistEntity> getArtistById(@PathVariable("id") final long id) {
        ArtistEntity artist = artistDAO.getArtistById(id);
        if (artist == null) {
            return new ResponseEntity<ArtistEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ArtistEntity>(artist, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistEntity>> getArtistsByCriteria(
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<ArtistEntity> artists = relationDAO.getElementsByCriteria(ArtistEntity.class, sort,
                order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistEntity>>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistEntity>> getArtistsByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<ArtistEntity> artists = relationDAO.getElementsByCriteria(ArtistEntity.class, sort,
                relation, id, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistEntity>>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = "/all"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistEntity>> getAllArtists() {
        List<ArtistEntity> artists = artistDAO.getAllArtists();
        if (artists == null) {
            return new ResponseEntity<List<ArtistEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistEntity>>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = "/page_count"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageCount() {
        List<ArtistEntity> artists = artistDAO.getAllArtists();
        int count = (int) Math.ceil(artists.size()
                / (double) CountElementsConstants.ArtistCountElements.ARTIST_FULL_COUNT_ELEMENTS);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }

    @RequestMapping(value = "/{relation}/{id}/page_count"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageCount(@PathVariable("id") final long id,
            @PathVariable("relation") final String relation) {
        int count = relationDAO.getSizeByCriteria(ArtistEntity.class, relation, id);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }

}
