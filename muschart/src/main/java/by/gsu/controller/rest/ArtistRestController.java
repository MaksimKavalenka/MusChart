package by.gsu.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.IArtistDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.model.Artist;

@RestController
public class ArtistRestController {

    @RequestMapping(value = "/artist/create/{name}/{photo}", method = RequestMethod.POST)
    public ResponseEntity<Void> createArtist(@PathVariable("name") final String name,
            @PathVariable("photo") final String photo) {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            artistDAO.createArtist(name, photo);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/artist/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artist> getArtistById(@PathVariable("id") final long id) {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            Artist artist = artistDAO.getArtistById(id);
            if (artist == null) {
                return new ResponseEntity<Artist>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Artist>(artist, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Artist>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/artist/{idFrom}/{idTo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artist>> getArtistsByIds(@PathVariable("idFrom") final long idFrom,
            @PathVariable("idTo") final long idTo) {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            List<Artist> artists = artistDAO.getArtistsByIds(idFrom, idTo);
            if (artists == null) {
                return new ResponseEntity<List<Artist>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Artist>>(artists, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Artist>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/artist/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artist>> getAllArtists() {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            List<Artist> artists = artistDAO.getAllArtists();
            if (artists == null) {
                return new ResponseEntity<List<Artist>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Artist>>(artists, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Artist>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/artist/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Artist> deleteArtistById(@PathVariable("id") final long id) {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            artistDAO.deleteArtistById(id);
            return new ResponseEntity<Artist>(HttpStatus.NO_CONTENT);
        } catch (ValidationException e) {
            return new ResponseEntity<Artist>(HttpStatus.CONFLICT);
        }
    }

}
