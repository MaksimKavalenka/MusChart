package com.muschart.service.database.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.TrackEntity;
import com.muschart.jpa.repository.TrackArtistRepository;
import com.muschart.jpa.repository.TrackRepository;
import com.muschart.service.database.dao.TrackDatabaseServiceDAO;
import com.muschart.utility.JpaHelper;
import com.muschart.utility.Parser;

@Service("trackDatabaseService")
public class TrackDatabaseService implements TrackDatabaseServiceDAO {

    @Autowired
    private TrackRepository       trackRepository;

    @Autowired
    private TrackArtistRepository trackArtistRepository;

    @Override
    public TrackEntity createTrack(String name, String song, String cover, String video, Date release, List<Long> artistsIds, List<Long> unitsIds, List<Long> genresIds) {
        TrackEntity track = new TrackEntity(name, song, cover, video, release);
        synchronized (TrackEntity.class) {
            trackRepository.save(track);
        }
        for (long artistId : artistsIds) {
            addArtistToTrack(track.getId(), artistId);
        }
        for (long unitId : unitsIds) {
            addUnitToTrack(track.getId(), unitId);
        }
        for (long genreId : genresIds) {
            addGenreToTrack(track.getId(), genreId);
        }
        return track;
    }

    @Override
    public void addArtistToTrack(long trackId, long artistId) {
        trackRepository.addArtistToTrack(trackId, artistId);
    }

    @Override
    public void addGenreToTrack(long trackId, long genreId) {
        trackRepository.addGenreToTrack(trackId, genreId);
    }

    @Override
    public void addUnitToTrack(long trackId, long unitId) {
        trackRepository.addUnitToTrack(trackId, unitId);
    }

    @Override
    public void deleteTrackById(long id) {
        trackRepository.delete(id);
    }

    @Override
    public TrackEntity getTrackById(long id) {
        return trackRepository.findOne(id);
    }

    @Override
    public List<TrackEntity> getTracks(int sort, boolean order, int page) {
        return trackRepository.findAll(JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getArtistTracks(long artistId, int sort, boolean order, int page) {
        return trackArtistRepository.getArtistTracks(artistId, JpaHelper.TRACK_ARTIST_TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getGenreTracks(long genreId, int sort, boolean order, int page) {
        return trackRepository.findByGenresId(genreId, JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getUserTracks(long userId, int sort, boolean order, int page) {
        return trackRepository.findByUsersId(userId, JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameDTO> getAllTracksIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(trackRepository.getAllTracksIdAndName());
    }

    @Override
    public int getTracksPagesCount() {
        return JpaHelper.TRACK.getPagesCount(trackRepository.count());
    }

    @Override
    public int getArtistTracksPagesCount(long artistId) {
        return JpaHelper.TRACK.getPagesCount(trackArtistRepository.getArtistTracksCount(artistId));
    }

    @Override
    public int getGenreTracksPagesCount(long genreId) {
        return JpaHelper.TRACK.getPagesCount(trackRepository.countByGenresId(genreId));
    }

    @Override
    public int getUserTracksPagesCount(long userId) {
        return JpaHelper.TRACK.getPagesCount(trackRepository.countByUsersId(userId));
    }

}