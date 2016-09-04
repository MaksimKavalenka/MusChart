package by.gsu.controller.rest;

import static by.gsu.constants.RestConstants.JSON_EXT;
import static by.gsu.constants.RestConstants.TRACKS_PATH;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.database.dao.TrackDAO;
import by.gsu.model.TrackModel;
import by.gsu.utility.ModelJsonParser;

@RestController
public class TrackRestController {

    @Autowired
    private TrackDAO trackDAO;

    @RequestMapping(value = TRACKS_PATH
            + "/create/{name}/{song}/{cover}/{video}/{release}/{artists}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<TrackModel> createTrack(@PathVariable("name") final String name,
            @PathVariable("song") final String song, @PathVariable("cover") final String cover,
            @PathVariable("video") String video, @PathVariable("release") final Date release,
            @PathVariable("artists") final String artists,
            @PathVariable("genres") final String genres) {
        if ("null".equals(video)) {
            video = "";
        }
        TrackModel track = trackDAO.createTrack(name, song, cover, video, release,
                ModelJsonParser.getUnits(artists), ModelJsonParser.getArtists(artists),
                ModelJsonParser.getGenres(genres));
        return new ResponseEntity<TrackModel>(track, HttpStatus.CREATED);
    }

    @RequestMapping(value = TRACKS_PATH + "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<TrackModel> deleteTrackById(@PathVariable("id") final long id) {
        trackDAO.deleteTrackById(id);
        return new ResponseEntity<TrackModel>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = TRACKS_PATH + "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrackModel> getTrackById(@PathVariable("id") final long id) {
        TrackModel track = trackDAO.getTrackById(id);
        if (track == null) {
            return new ResponseEntity<TrackModel>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<TrackModel>(track, HttpStatus.OK);
    }

    @RequestMapping(value = TRACKS_PATH + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrackModel>> getTracksByCriteria(
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<TrackModel> tracks = trackDAO.getTracksByCriteria(sort, order, page);
        if (tracks == null) {
            return new ResponseEntity<List<TrackModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TrackModel>>(tracks, HttpStatus.OK);
    }

    @RequestMapping(value = TRACKS_PATH + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackModel>> getAllTracks() {
        List<TrackModel> tracks = trackDAO.getAllTracks();
        return new ResponseEntity<List<TrackModel>>(tracks, HttpStatus.OK);
    }

}
