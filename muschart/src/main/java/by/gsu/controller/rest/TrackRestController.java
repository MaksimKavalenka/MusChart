package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.TRACKS_PATH;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.ITrackDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.TrackFactory;
import by.gsu.model.Track;
import by.gsu.parser.AmplitudeJsonParser;
import by.gsu.parser.ModelJsonParser;

@RestController
public class TrackRestController {

    @RequestMapping(value = TRACKS_PATH
            + "/create/{name}/{song}/{cover}/{video}/{release}/{artists}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> createTrack(@PathVariable("name") final String name,
            @PathVariable("song") final String song, @PathVariable("cover") final String cover,
            @PathVariable("video") String video, @PathVariable("release") final Date release,
            @PathVariable("artists") final String artists,
            @PathVariable("genres") final String genres) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            if ("null".equals(video)) {
                video = "";
            }
            trackDAO.createTrack(name, song, cover, video, release,
                    ModelJsonParser.getUnits(artists), ModelJsonParser.getArtists(artists),
                    ModelJsonParser.getGenres(genres));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Track> getTrackById(@PathVariable("id") final long id) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            Track track = trackDAO.getTrackById(id);
            if (track == null) {
                return new ResponseEntity<Track>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Track>(track, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Track>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Track>> getTracksByCriteria(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByCriteria(sort, order, page);
            if (tracks == null) {
                return new ResponseEntity<List<Track>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Track>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/amplitude/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAmplitudeTracksByCriteria(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByCriteria(sort, order, page);
            if (tracks == null) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<String>(AmplitudeJsonParser.getAmplitudeJson(tracks),
                    HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<Track>> getAllTracks() {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getAllTracks();
            return new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Track>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<Track> deleteTrackById(@PathVariable("id") final long id) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            trackDAO.deleteTrackById(id);
            return new ResponseEntity<Track>(HttpStatus.NO_CONTENT);
        } catch (ValidationException e) {
            return new ResponseEntity<Track>(HttpStatus.CONFLICT);
        }
    }

}
