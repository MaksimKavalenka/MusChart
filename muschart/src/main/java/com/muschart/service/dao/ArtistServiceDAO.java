package com.muschart.service.dao;

import java.util.List;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.ArtistEntity;

public interface ArtistServiceDAO {

    ArtistEntity createArtist(String name, String photo, List<Long> genresIds);

    void addGenreToArtist(long artistId, long genreId);

    void deleteArtistById(long id);

    ArtistEntity getArtistById(long id);

    List<ArtistEntity> getArtists(int sort, boolean order, int page);

    List<ArtistEntity> getGenreArtists(long genreId, int sort, boolean order, int page);

    List<ArtistEntity> getTrackArtists(long trackId, int sort, boolean order, int page);

    List<ArtistEntity> getUserArtists(long userId, int sort, boolean order, int page);

    List<IdAndNameDTO> getAllArtistsIdAndName();

    List<IdAndNameDTO> getTrackArtistsIdAndName(long trackId);

    int getArtistsPagesCount();

    int getGenreArtistsPagesCount(long genreId);

    int getTrackArtistsPagesCount(long trackId);

    int getUserArtistsPagesCount(long userId);

}