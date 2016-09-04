package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.ARTISTS_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.ArtistDAO;
import by.gsu.model.ArtistModel;
import by.gsu.utility.ModelJsonParser;

@RestController
public class ArtistRestController {

    @Autowired
    private ArtistDAO artistDAO;

    @RequestMapping(value = ARTISTS_PATH + "/create/{name}/{photo}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<ArtistModel> createArtist(@PathVariable("name") final String name,
            @PathVariable("photo") final String photo,
            @PathVariable("genres") final String genres) {
        ArtistModel artist = artistDAO.createArtist(name, photo, ModelJsonParser.getGenres(genres));
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

    @RequestMapping(value = ARTISTS_PATH
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtistModel>> getAllArtists() {
        List<ArtistModel> artists = artistDAO.getAllArtists();
        if (artists == null) {
            return new ResponseEntity<List<ArtistModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ArtistModel>>(artists, HttpStatus.OK);
    }

}
