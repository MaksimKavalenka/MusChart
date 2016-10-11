package by.gsu.controller.rest;

import static by.gsu.constants.UrlConstants.JSON_EXT;
import static by.gsu.constants.UrlConstants.Rest.TRACKS_URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.bean.TrackInfo;
import by.gsu.constants.EntityConstants.Structure.Entities;
import by.gsu.entity.TrackEntity;
import by.gsu.jpa.service.dao.ArtistServiceDAO;
import by.gsu.jpa.service.dao.TrackServiceDAO;
import by.gsu.jpa.service.dao.UnitServiceDAO;
import by.gsu.utility.Parser;
import by.gsu.utility.Secure;

@RestController
@RequestMapping(TRACKS_URL)
public class TrackRestController {

    @Autowired
    private ArtistServiceDAO artistService;
    @Autowired
    private TrackServiceDAO  trackService;
    @Autowired
    private UnitServiceDAO   unitService;

    @RequestMapping(value = "/create/{name}/{song}/{cover}/{video}/{release}/{artists}/{units}/{genres}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<TrackEntity> createTrack(@PathVariable("name") final String name,
            @PathVariable("song") final String song, @PathVariable("cover") final String cover,
            @PathVariable("video") final String video, @PathVariable("release") final Date release,
            @PathVariable("units") final String units,
            @PathVariable("artists") final String artists,
            @PathVariable("genres") final String genres) {
        TrackEntity track = trackService.createTrack(name, song, cover, video, release,
                Parser.getIdsFromJson(artists), Parser.getIdsFromJson(units),
                Parser.getIdsFromJson(genres));
        return new ResponseEntity<TrackEntity>(track, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.DELETE)
    public ResponseEntity<TrackEntity> deleteTrackById(@PathVariable("id") final long id) {
        trackService.deleteTrackById(id);
        return new ResponseEntity<TrackEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<TrackEntity> getTrackById(@PathVariable("id") final long id) {
        TrackEntity track = trackService.getTrackById(id);
        if (track == null) {
            return new ResponseEntity<TrackEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<TrackEntity>(track, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sort}/{order}/{page}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackInfo>> getTracks(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<TrackEntity> tracks = trackService.getTracks(sort, order, page);
        if (tracks == null) {
            return new ResponseEntity<List<TrackInfo>>(HttpStatus.NO_CONTENT);
        }

        List<TrackInfo> trackInfos = new ArrayList<>(tracks.size());
        for (TrackEntity track : tracks) {
            List<IdAndNameEntity> artists = artistService.getTrackArtistsIdAndName(track.getId());
            List<IdAndNameEntity> units = unitService.getTrackUnitsIdAndName(track.getId());
            trackInfos.add(new TrackInfo(track, artists, units));
        }
        return new ResponseEntity<List<TrackInfo>>(trackInfos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackInfo>> getEntityTracks(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId, @PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<TrackEntity> tracks = null;
        switch (entity) {
            case Entities.ARTIST:
                tracks = trackService.getArtistTracks(entityId, sort, order, page);
                break;
            case Entities.GENRE:
                tracks = trackService.getGenreTracks(entityId, sort, order, page);
                break;
            default:
                break;
        }
        if (tracks == null) {
            return new ResponseEntity<List<TrackInfo>>(HttpStatus.NO_CONTENT);
        }

        List<TrackInfo> trackInfos = new ArrayList<>(tracks.size());
        for (TrackEntity track : tracks) {
            List<IdAndNameEntity> artists = artistService.getTrackArtistsIdAndName(track.getId());
            List<IdAndNameEntity> units = unitService.getTrackUnitsIdAndName(track.getId());
            trackInfos.add(new TrackInfo(track, artists, units));
        }
        return new ResponseEntity<List<TrackInfo>>(trackInfos, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{sort}/{order}/{page}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackInfo>> getUserTracks(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<TrackEntity> tracks = trackService.getUserTracks(Secure.getLoggedUser().getId(), sort,
                order, page);
        if (tracks == null) {
            return new ResponseEntity<List<TrackInfo>>(HttpStatus.NO_CONTENT);
        }

        List<TrackInfo> trackInfos = new ArrayList<>(tracks.size());
        for (TrackEntity track : tracks) {
            List<IdAndNameEntity> artists = artistService.getTrackArtistsIdAndName(track.getId());
            List<IdAndNameEntity> units = unitService.getTrackUnitsIdAndName(track.getId());
            trackInfos.add(new TrackInfo(track, artists, units));
        }
        return new ResponseEntity<List<TrackInfo>>(trackInfos, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/id_name" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameEntity>> getAllGenresIdAndName() {
        List<IdAndNameEntity> tracksIdAndName = trackService.getAllTracksIdAndName();
        if (tracksIdAndName == null) {
            return new ResponseEntity<List<IdAndNameEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameEntity>>(tracksIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = "/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getTracksPagesCount() {
        int pagesCount = trackService.getTracksPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{entity}/{entityId}/pages_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getEntityTracksPagesCount(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        int pagesCount = 0;
        switch (entity) {
            case Entities.ARTIST:
                pagesCount = trackService.getArtistTracksPagesCount(entityId);
                break;
            case Entities.GENRE:
                pagesCount = trackService.getGenreTracksPagesCount(entityId);
                break;
            default:
                break;
        }
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserTracksPagesCount(
            @PathVariable("entity") final String entity,
            @PathVariable("entityId") final long entityId) {
        int pagesCount = trackService.getUserTracksPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

}
