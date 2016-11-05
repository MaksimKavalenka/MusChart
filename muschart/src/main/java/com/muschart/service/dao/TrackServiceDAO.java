package com.muschart.service.dao;

import java.util.Date;
import java.util.List;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.TrackEntity;

public interface TrackServiceDAO {

    TrackEntity createTrack(String name, String song, String cover, String video, Date release,
            List<Long> artistsIds, List<Long> unitsIds, List<Long> genresIds);

    void addArtistToTrack(long trackId, long artistId);

    void addGenreToTrack(long trackId, long genreId);

    void addUnitToTrack(long trackId, long unitId);

    void deleteTrackById(long id);

    TrackEntity getTrackById(long id);

    List<TrackEntity> getTracks(int sort, boolean order, int page);

    List<TrackEntity> getArtistTracks(long artistId, int sort, boolean order, int page);

    List<TrackEntity> getGenreTracks(long genreId, int sort, boolean order, int page);

    List<TrackEntity> getUserTracks(long userId, int sort, boolean order, int page);

    List<IdAndNameDTO> getAllTracksIdAndName();

    int getTracksPagesCount();

    int getArtistTracksPagesCount(long artistId);

    int getGenreTracksPagesCount(long genreId);

    int getUserTracksPagesCount(long userId);

}
