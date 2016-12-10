package com.muschart.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muschart.entity.TrackEntity;

@Service("trackRepository")
public interface TrackRepository extends CrudRepository<TrackEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO track_artist(track_id, artist_id) VALUES (?1, ?2)", nativeQuery = true)
    void addArtistToTrack(long trackId, long artistId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO track_genre(id_track, id_genre) VALUES (?1, ?2)", nativeQuery = true)
    void addGenreToTrack(long trackId, long genreId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO track_unit(track_id, unit_id) VALUES (?1, ?2)", nativeQuery = true)
    void addUnitToTrack(long trackId, long unitId);

    @Query("FROM TrackEntity")
    List<TrackEntity> findAll(Pageable pageable);

    List<TrackEntity> findByGenresId(long genreId, Pageable pageable);

    List<TrackEntity> findByUsersId(long userId, Pageable pageable);

    @Query("SELECT track.id, track.name FROM TrackEntity track")
    List<Object[]> getAllTracksIdAndName();

    @Modifying
    @Transactional
    @Query(value = "UPDATE track SET track.rating = track.rating + 1 WHERE track.id = ?1", nativeQuery = true)
    void setTrackLike(long trackId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE track SET track.rating = track.rating - 1 WHERE track.id = ?1", nativeQuery = true)
    void setTrackDislike(long trackId);

    long countByGenresId(long genreId);

    long countByUsersId(long userId);

}