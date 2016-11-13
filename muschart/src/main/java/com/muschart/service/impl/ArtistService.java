package com.muschart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.ArtistEntity;
import com.muschart.jpa.repository.ArtistRepository;
import com.muschart.jpa.repository.TrackArtistRepository;
import com.muschart.service.dao.ArtistServiceDAO;
import com.muschart.utility.JpaHelper;
import com.muschart.utility.Parser;

public class ArtistService implements ArtistServiceDAO {

    @Autowired
    private ArtistRepository      repository;
    @Autowired
    private TrackArtistRepository trackArtistRepository;

    @Override
    public ArtistEntity createArtist(String name, String photo, List<Long> genresIds) {
        ArtistEntity artist = new ArtistEntity(name, photo);
        synchronized (ArtistEntity.class) {
            repository.save(artist);
        }
        for (long genreId : genresIds) {
            addGenreToArtist(artist.getId(), genreId);
        }
        return artist;
    }

    @Override
    public void addGenreToArtist(long artistId, long genreId) {
        repository.addGenreToArtist(artistId, genreId);
    }

    @Override
    public void deleteArtistById(long id) {
        repository.delete(id);
    }

    @Override
    public ArtistEntity getArtistById(long id) {
        return repository.findOne(id);
    }

    @Override
    public List<ArtistEntity> getArtists(int sort, boolean order, int page) {
        return repository.findAll(JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getGenreArtists(long genreId, int sort, boolean order, int page) {
        return repository.findByGenresId(genreId,
                JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getTrackArtists(long trackId, int sort, boolean order, int page) {
        return trackArtistRepository.getTrackArtists(trackId,
                JpaHelper.TRACK_ARTIST_ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getUserArtists(long userId, int sort, boolean order, int page) {
        return repository.findByUsersId(userId, JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameDTO> getAllArtistsIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(repository.getAllArtistsIdAndName());
    }

    @Override
    public List<IdAndNameDTO> getTrackArtistsIdAndName(long trackId) {
        return Parser.parseObjectsToIdAndNameEntities(repository.getTrackArtistsIdAndName(trackId));
    }

    @Override
    public int getArtistsPagesCount() {
        return JpaHelper.ARTIST.getPagesCount(repository.count());
    }

    @Override
    public int getGenreArtistsPagesCount(long genreId) {
        return JpaHelper.ARTIST.getPagesCount(repository.countByGenresId(genreId));
    }

    @Override
    public int getTrackArtistsPagesCount(long trackId) {
        return JpaHelper.ARTIST.getPagesCount(trackArtistRepository.getTrackArtistsCount(trackId));
    }

    @Override
    public int getUserArtistsPagesCount(long userId) {
        return JpaHelper.ARTIST.getPagesCount(repository.countByUsersId(userId));
    }

}
