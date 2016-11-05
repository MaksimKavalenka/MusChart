package com.muschart.controller.rest;

import static com.muschart.constants.UrlConstants.Rest.JSON_EXT;
import static com.muschart.constants.UrlConstants.Rest.TRACKS_URL;
import static com.muschart.constants.UrlConstants.Rest.Operation.CREATE_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.DELETE_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.GET_OPERATION;
import static com.muschart.constants.UrlConstants.Rest.Operation.USER_OPERATION;

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

import com.muschart.constants.EntityConstants.Structure.Entities;
import com.muschart.dto.IdAndNameDTO;
import com.muschart.dto.TrackDTO;
import com.muschart.entity.TrackEntity;
import com.muschart.entity.UserEntity;
import com.muschart.service.dao.ArtistServiceDAO;
import com.muschart.service.dao.TrackServiceDAO;
import com.muschart.service.dao.UnitServiceDAO;
import com.muschart.service.dao.UserServiceDAO;
import com.muschart.utility.Parser;
import com.muschart.utility.Secure;

@RestController
@RequestMapping(TRACKS_URL)
public class TrackRestController {

    @Autowired
    private ArtistServiceDAO artistService;
    @Autowired
    private TrackServiceDAO  trackService;
    @Autowired
    private UnitServiceDAO   unitService;
    @Autowired
    private UserServiceDAO   userService;

    @RequestMapping(value = CREATE_OPERATION
            + "/{name}/{song}/{cover}/{video}/{release}/{artists}/{units}/{genres}", method = RequestMethod.POST)
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

    @RequestMapping(value = DELETE_OPERATION + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TrackEntity> deleteTrackById(@PathVariable("id") final long id) {
        trackService.deleteTrackById(id);
        return new ResponseEntity<TrackEntity>(HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{id}" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<TrackEntity> getTrackById(@PathVariable("id") final long id) {
        TrackEntity track = trackService.getTrackById(id);
        if (track == null) {
            return new ResponseEntity<TrackEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<TrackEntity>(track, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackDTO>> getTracks(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<TrackEntity> tracks = trackService.getTracks(sort, order, page);
        if (tracks == null) {
            return new ResponseEntity<List<TrackDTO>>(HttpStatus.NO_CONTENT);
        }

        List<TrackDTO> tracksDto = new ArrayList<>(tracks.size());
        for (TrackEntity track : tracks) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isTrackLiked(user.getId(), track.getId());
            List<IdAndNameDTO> artists = artistService.getTrackArtistsIdAndName(track.getId());
            List<IdAndNameDTO> units = unitService.getTrackUnitsIdAndName(track.getId());
            tracksDto.add(new TrackDTO(track, isLiked, artists, units));
        }
        return new ResponseEntity<List<TrackDTO>>(tracksDto, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{entity}/{entityId}/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackDTO>> getEntityTracks(
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
            return new ResponseEntity<List<TrackDTO>>(HttpStatus.NO_CONTENT);
        }

        List<TrackDTO> tracksDto = new ArrayList<>(tracks.size());
        for (TrackEntity track : tracks) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isTrackLiked(user.getId(), track.getId());
            List<IdAndNameDTO> artists = artistService.getTrackArtistsIdAndName(track.getId());
            List<IdAndNameDTO> units = unitService.getTrackUnitsIdAndName(track.getId());
            tracksDto.add(new TrackDTO(track, isLiked, artists, units));
        }
        return new ResponseEntity<List<TrackDTO>>(tracksDto, HttpStatus.OK);
    }

    @RequestMapping(value = USER_OPERATION + "/{sort}/{order}/{page}"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TrackDTO>> getUserTracks(@PathVariable("sort") final int sort,
            @PathVariable("order") final boolean order, @PathVariable("page") final int page) {
        List<TrackEntity> tracks = trackService.getUserTracks(Secure.getLoggedUser().getId(), sort,
                order, page);
        if (tracks == null) {
            return new ResponseEntity<List<TrackDTO>>(HttpStatus.NO_CONTENT);
        }

        List<TrackDTO> tracksDto = new ArrayList<>(tracks.size());
        for (TrackEntity track : tracks) {
            UserEntity user = Secure.getLoggedUser();
            boolean isLiked = (user == null) ? false
                    : userService.isTrackLiked(user.getId(), track.getId());
            List<IdAndNameDTO> artists = artistService.getTrackArtistsIdAndName(track.getId());
            List<IdAndNameDTO> units = unitService.getTrackUnitsIdAndName(track.getId());
            tracksDto.add(new TrackDTO(track, isLiked, artists, units));
        }
        return new ResponseEntity<List<TrackDTO>>(tracksDto, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/all/id_name" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<IdAndNameDTO>> getAllGenresIdAndName() {
        List<IdAndNameDTO> tracksIdAndName = trackService.getAllTracksIdAndName();
        if (tracksIdAndName == null) {
            return new ResponseEntity<List<IdAndNameDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IdAndNameDTO>>(tracksIdAndName, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getTracksPagesCount() {
        int pagesCount = trackService.getTracksPagesCount();
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

    @RequestMapping(value = GET_OPERATION + "/{entity}/{entityId}/pages_count"
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

    @RequestMapping(value = USER_OPERATION + "/pages_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserTracksPagesCount() {
        int pagesCount = trackService.getUserTracksPagesCount(Secure.getLoggedUser().getId());
        return new ResponseEntity<Integer>(pagesCount, HttpStatus.OK);
    }

}
