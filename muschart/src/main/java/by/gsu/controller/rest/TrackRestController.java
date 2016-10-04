package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.Rest.TRACKS_URL;

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

import by.gsu.constants.CountElementsConstants;
import by.gsu.database.dao.RelationDAO;
import by.gsu.database.dao.TrackDAO;
import by.gsu.entity.TrackEntity;

@RestController
@RequestMapping(TRACKS_URL)
public class TrackRestController extends by.gsu.controller.rest.RestController {

    @Autowired
    private RelationDAO relationDAO;
    @Autowired
    private TrackDAO    trackDAO;

    @RequestMapping(value = "/create/{name}/{song}/{cover}/{video}/{release}/{units}/{artists}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<TrackEntity> createTrack(@PathVariable("name") final String name,
            @PathVariable("song") final String song, @PathVariable("cover") final String cover,
            @PathVariable("video") final String video, @PathVariable("release") final Date release,
            @PathVariable("units") final String units,
            @PathVariable("artists") final String artists,
            @PathVariable("genres") final String genres) {
        TrackEntity track = trackDAO.createTrack(name, song, cover, video, release, getUnits(units),
                getArtists(artists), getGenres(genres));
        return new ResponseEntity<TrackEntity>(track, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<TrackEntity> deleteTrackById(@PathVariable("id") final long id) {
        trackDAO.deleteTrackById(id);
        return new ResponseEntity<TrackEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrackEntity> getTrackById(@PathVariable("id") final long id) {
        TrackEntity track = trackDAO.getTrackById(id);
        if (track == null) {
            return new ResponseEntity<TrackEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<TrackEntity>(track, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrackEntity>> getTracksByCriteria(
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<TrackEntity> tracks = relationDAO.getElementsByCriteria(TrackEntity.class, sort, order,
                page);
        if (tracks == null) {
            return new ResponseEntity<List<TrackEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TrackEntity>>(tracks, HttpStatus.OK);
    }

    @RequestMapping(value = "/{relation}/{id}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrackEntity>> getTracksByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("sort") final int sort, @PathVariable("order") final boolean order,
            @PathVariable("page") final int page) {
        List<TrackEntity> tracks = relationDAO.getElementsByCriteria(TrackEntity.class, sort,
                relation, id, order, page);
        if (tracks == null) {
            return new ResponseEntity<List<TrackEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TrackEntity>>(tracks, HttpStatus.OK);
    }

    @RequestMapping(value = "/all" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackEntity>> getAllTracks() {
        List<TrackEntity> tracks = trackDAO.getAllTracks();
        return new ResponseEntity<List<TrackEntity>>(tracks, HttpStatus.OK);
    }

    @RequestMapping(value = "/{relation}/{id}/page_amount"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageAmount(@PathVariable("id") final long id,
            @PathVariable("relation") final String relation) {
        int amount = relationDAO.getSizeByCriteria(TrackEntity.class, relation, id);
        return new ResponseEntity<Integer>(amount, HttpStatus.OK);
    }

    @RequestMapping(value = "/page_amount"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPageAmount() {
        List<TrackEntity> tracks = trackDAO.getAllTracks();
        int amount = (int) Math.ceil(tracks.size()
                / (double) CountElementsConstants.TrackCountElements.TRACK_FULL_COUNT_ELEMENTS);
        return new ResponseEntity<Integer>(amount, HttpStatus.OK);
    }

}
