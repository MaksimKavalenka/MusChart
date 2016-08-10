package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.TRACKS_PATH;
import static by.gsu.constants.UploadConstants.Path.AUDIO_UPLOAD_PATH;
import static by.gsu.constants.UploadConstants.Path.TRACK_COVER_UPLOAD_PATH;

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
            + "/create/{songName}/{song}/{cover}/{artists}/{genres}/{date}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> createTrack(@PathVariable("songName") final String songName,
            @PathVariable("song") final String song, @PathVariable("cover") final String cover,
            @PathVariable("artists") final String artists,
            @PathVariable("genres") final String genres, @PathVariable("date") final Date date) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            trackDAO.createTrack(songName, AUDIO_UPLOAD_PATH + "/" + song,
                    TRACK_COVER_UPLOAD_PATH + "/" + cover, ModelJsonParser.getUnits(artists),
                    ModelJsonParser.getArtists(artists), ModelJsonParser.getGenres(genres), date);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/id/asc/{idFrom}/{idTo}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Track>> getTracksByIdsAsc(@PathVariable("idFrom") final long idFrom,
            @PathVariable("idTo") final long idTo) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByIdsAsc(idFrom, idTo);
            if (tracks == null) {
                return new ResponseEntity<List<Track>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Track>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/id/desc/{idFrom}/{idTo}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Track>> getTracksByIdsDesc(@PathVariable("idFrom") final long idFrom,
            @PathVariable("idTo") final long idTo) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByIdsDesc(idFrom, idTo);
            if (tracks == null) {
                return new ResponseEntity<List<Track>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<List<Track>>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/id/asc/amplitude/{idFrom}/{idTo}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAmplitudeTracksByIdsAsc(
            @PathVariable("idFrom") final long idFrom, @PathVariable("idTo") final long idTo) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByIdsAsc(idFrom, idTo);
            if (tracks == null) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<String>(AmplitudeJsonParser.getAmplitudeJson(tracks),
                    HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TRACKS_PATH + "/id/desc/amplitude/{idFrom}/{idTo}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAmplitudeTracksByIdsDesc(
            @PathVariable("idFrom") final long idFrom, @PathVariable("idTo") final long idTo) {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            List<Track> tracks = trackDAO.getTracksByIdsDesc(idFrom, idTo);
            if (tracks == null) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<String>(AmplitudeJsonParser.getAmplitudeJson(tracks),
                    HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
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
