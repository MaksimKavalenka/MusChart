package com.muschart.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.muschart.entity.ArtistEntity;
import com.muschart.entity.TrackArtistEntity;
import com.muschart.entity.TrackEntity;

public interface TrackArtistRepository extends CrudRepository<TrackArtistEntity, Long> {

    @Query("SELECT artist FROM TrackArtistEntity trackArtist JOIN trackArtist.artist artist JOIN trackArtist.track track WHERE track.id = ?1")
    List<ArtistEntity> getTrackArtists(long trackId, Pageable pageable);

    @Query("SELECT COUNT(*) FROM TrackArtistEntity trackArtist JOIN trackArtist.track track WHERE track.id = ?1")
    long getTrackArtistsCount(long trackId);

    @Query("SELECT track FROM TrackArtistEntity trackArtist JOIN trackArtist.artist artist JOIN trackArtist.track track WHERE artist.id = ?1")
    List<TrackEntity> getArtistTracks(long artistId, Pageable pageable);

    @Query("SELECT COUNT(*) FROM TrackArtistEntity trackArtist JOIN trackArtist.artist artist WHERE artist.id = ?1")
    long getArtistTracksCount(long artistId);

}
