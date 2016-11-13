package com.muschart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.TrackEntity;
import com.muschart.jpa.repository.TrackArtistRepository;
import com.muschart.jpa.repository.TrackRepository;
import com.muschart.service.dao.TrackServiceDAO;
import com.muschart.utility.JpaHelper;
import com.muschart.utility.Parser;

public class TrackService implements TrackServiceDAO {

    @Autowired
    private TrackRepository       repository;
    @Autowired
    private TrackArtistRepository trackArtistRepository;

    @Override
    public TrackEntity createTrack(String name, String song, String cover, String video,
            Date release, List<Long> artistsIds, List<Long> unitsIds, List<Long> genresIds) {
        TrackEntity track = new TrackEntity(name, song, cover, video, release);
        synchronized (TrackEntity.class) {
            repository.save(track);
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
        repository.addArtistToTrack(trackId, artistId);
    }

    @Override
    public void addGenreToTrack(long trackId, long genreId) {
        repository.addGenreToTrack(trackId, genreId);
    }

    @Override
    public void addUnitToTrack(long trackId, long unitId) {
        repository.addUnitToTrack(trackId, unitId);
    }

    @Override
    public void deleteTrackById(long id) {
        repository.delete(id);
    }

    @Override
    public TrackEntity getTrackById(long id) {
        return repository.findOne(id);
    }

    @Override
    public List<TrackEntity> getTracks(int sort, boolean order, int page) {
        return repository.findAll(JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getArtistTracks(long artistId, int sort, boolean order, int page) {
        return trackArtistRepository.getArtistTracks(artistId,
                JpaHelper.TRACK_ARTIST_TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getGenreTracks(long genreId, int sort, boolean order, int page) {
        return repository.findByGenresId(genreId,
                JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getUserTracks(long userId, int sort, boolean order, int page) {
        return repository.findByUsersId(userId, JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameDTO> getAllTracksIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(repository.getAllTracksIdAndName());
    }

    @Override
    public int getTracksPagesCount() {
        return JpaHelper.TRACK.getPagesCount(repository.count());
    }

    @Override
    public int getArtistTracksPagesCount(long artistId) {
        return JpaHelper.TRACK.getPagesCount(trackArtistRepository.getArtistTracksCount(artistId));
    }

    @Override
    public int getGenreTracksPagesCount(long genreId) {
        return JpaHelper.TRACK.getPagesCount(repository.countByGenresId(genreId));
    }

    @Override
    public int getUserTracksPagesCount(long userId) {
        return JpaHelper.TRACK.getPagesCount(repository.countByUsersId(userId));
    }

}
