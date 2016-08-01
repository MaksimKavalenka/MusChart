package by.gsu.controller.rest;

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

@RestController
public class TrackRestController {

    @RequestMapping(value = "/track/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/track/{idFrom}/{idTo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Track>> getTracksByIds(@PathVariable("idFrom") final long idFrom,
            @PathVariable("idTo") final long idTo) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByIds(idFrom, idTo);
            if (tracks == null) {
                return new ResponseEntity<List<Track>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Track>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/track/amplitude/{idFrom}/{idTo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAmplitudeTracksByIds(@PathVariable("idFrom") final long idFrom,
            @PathVariable("idTo") final long idTo) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByIds(idFrom, idTo);
            if (tracks == null) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<String>(AmplitudeJsonParser.getAmplitudeJson(tracks),
                    HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/track/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Track>> getAllTracks() {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getAllTracks();
            if (tracks == null) {
                return new ResponseEntity<List<Track>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Track>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/track/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Track> deleteTrackById(@PathVariable("id") final long id) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            trackDAO.deleteTrackById(id);
            return new ResponseEntity<Track>(HttpStatus.NO_CONTENT);
        } catch (ValidationException e) {
            return new ResponseEntity<Track>(HttpStatus.CONFLICT);
        }
    }

}
