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
    public ArtistEntity createArtist(final String name, final String photo,
            final List<Long> genresIds) {
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
    public void addGenreToArtist(final long artistId, final long genreId) {
        repository.addGenreToArtist(artistId, genreId);
    }

    @Override
    public void deleteArtistById(final long id) {
        repository.delete(id);
    }

    @Override
    public ArtistEntity getArtistById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public List<ArtistEntity> getArtists(final int sort, final boolean order, final int page) {
        return repository.findAll(JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getGenreArtists(final long genreId, final int sort,
            final boolean order, final int page) {
        return repository.findByGenresId(genreId,
                JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getTrackArtists(final long trackId, final int sort,
            final boolean order, final int page) {
        return trackArtistRepository.getTrackArtists(trackId,
                JpaHelper.TRACK_ARTIST_ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getUserArtists(final long userId, final int sort, final boolean order,
            final int page) {
        return repository.findByUsersId(userId, JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameDTO> getAllArtistsIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(repository.getAllArtistsIdAndName());
    }

    @Override
    public List<IdAndNameDTO> getTrackArtistsIdAndName(final long trackId) {
        return Parser.parseObjectsToIdAndNameEntities(repository.getTrackArtistsIdAndName(trackId));
    }

    @Override
    public int getArtistsPagesCount() {
        return JpaHelper.ARTIST.getPagesCount(repository.count());
    }

    @Override
    public int getGenreArtistsPagesCount(final long genreId) {
        return JpaHelper.ARTIST.getPagesCount(repository.countByGenresId(genreId));
    }

    @Override
    public int getTrackArtistsPagesCount(final long trackId) {
        return JpaHelper.ARTIST.getPagesCount(trackArtistRepository.getTrackArtistsCount(trackId));
    }

    @Override
    public int getUserArtistsPagesCount(final long userId) {
        return JpaHelper.ARTIST.getPagesCount(repository.countByUsersId(userId));
    }

}