package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.ARTISTS_PATH;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.constants.CountElementsConstants;
import by.gsu.constants.ModelStructureConstants.Models;
import by.gsu.model.ArtistModel;

@RestController
public class ArtistRestController extends by.gsu.controller.rest.RestController {

    @RequestMapping(value = ARTISTS_PATH + "/create/{name}/{photo}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<ArtistModel> createArtist(@PathVariable("name") final String name,
            @PathVariable("photo") final String photo,
            @PathVariable("genres") final String genres) {
        ArtistModel artist = artistDAO.createArtist(name, photo, getGenres(genres));
        return new ResponseEntity<ArtistModel>(artist, HttpStatus.CREATED);
    }

    @RequestMapping(value = ARTISTS_PATH + "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<ArtistModel> deleteArtistById(@PathVariable("id") final long id) {
        artistDAO.deleteArtistById(id);
        return new ResponseEntity<ArtistModel>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = ARTISTS_PATH + "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistModel> getArtistById(@PathVariable("id") final long id) {
        ArtistModel artist = artistDAO.getArtistById(id);
        if (artist == null) {
            return new ResponseEntity<ArtistModel>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ArtistModel>(artist, HttpStatus.OK);
    }

    @RequestMapping(value = ARTISTS_PATH + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistModel>> getArtistsByCriteria(
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<ArtistModel> artists = artistDAO.getArtistsByCriteria(sort, order, page);
        if (artists == null) {
            return new ResponseEntity<List<ArtistModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistModel>>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = ARTISTS_PATH + "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistModel>> getArtistsByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<ArtistModel> artists = null;
        switch (relation) {
            case Models.GENRE:
                artists = artistDAO.getGenreArtistsByCriteria(id, sort, order, page);
                break;
            case Models.TRACK:
                artists = artistDAO.getTrackArtistsByCriteria(id, sort, order, page);
                break;
            case Models.USER:
                artists = artistDAO.getUserArtistsByCriteria(id, sort, order, page);
                break;
            default:
                break;
        }
        if (artists == null) {
            return new ResponseEntity<List<ArtistModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistModel>>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = ARTISTS_PATH
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistModel>> getAllArtists() {
        List<ArtistModel> artists = artistDAO.getAllArtists();
        if (artists == null) {
            return new ResponseEntity<List<ArtistModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistModel>>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = ARTISTS_PATH + "/page_amount"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageAmount() {
        List<ArtistModel> artists = artistDAO.getAllArtists();
        int amount = (int) Math.ceil(artists.size()
                / (double) CountElementsConstants.ArtistCountElements.ARTIST_FULL_COUNT_ELEMENTS);
        return new ResponseEntity<Integer>(amount, HttpStatus.OK);
    }

    @RequestMapping(value = ARTISTS_PATH + "/{id}/{relation}/page_amount"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageAmount(@PathVariable("id") final long id,
            @PathVariable("relation") final String relation) {
        int amount = 0;
        ArtistModel artist = artistDAO.getArtistById(id);
        switch (relation) {
            case Models.GENRE:
                amount = (int) Math.ceil(artist.getGenres().size()
                        / (double) CountElementsConstants.GenreCountElements.GENRE_FULL_COUNT_ELEMENTS);
                break;
            case Models.TRACK:
                amount = (int) Math.ceil(artist.getTracks().size()
                        / (double) CountElementsConstants.TrackCountElements.TRACK_FULL_COUNT_ELEMENTS);
                break;
            default:
                break;
        }
        return new ResponseEntity<Integer>(amount, HttpStatus.OK);
    }

}
