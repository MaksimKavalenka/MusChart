package com.muschart.service.impl;

import static com.muschart.constants.MessageConstants.TAKEN_GENRE_MESSAGE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.GenreEntity;
import com.muschart.exception.ValidationException;
import com.muschart.jpa.repository.GenreRepository;
import com.muschart.service.dao.GenreServiceDAO;
import com.muschart.utility.JpaHelper;
import com.muschart.utility.Parser;

public class GenreService implements GenreServiceDAO {

    @Autowired
    private GenreRepository repository;

    @Override
    public GenreEntity createGenre(String name) throws ValidationException {
        GenreEntity genre = new GenreEntity(name);
        synchronized (GenreEntity.class) {
            if (!checkGenreName(name)) {
                return repository.save(genre);
            } else {
                throw new ValidationException(TAKEN_GENRE_MESSAGE);
            }
        }
    }

    @Override
    public void deleteGenreById(long id) {
        repository.delete(id);
    }

    @Override
    public GenreEntity getGenreById(long id) {
        return repository.findOne(id);
    }

    @Override
    public List<GenreEntity> getGenres(int sort, boolean order, int page) {
        return repository.findAll(JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getArtistGenres(long artistId, int sort, boolean order, int page) {
        return repository.findByArtistsId(artistId,
                JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getTrackGenres(long trackId, int sort, boolean order, int page) {
        return repository.findByTracksId(trackId,
                JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getUserGenres(long userId, int sort, boolean order, int page) {
        return repository.findByUsersId(userId, JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameDTO> getAllGenresIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(repository.getAllGenresIdAndName());
    }

    @Override
    public int getGenresPagesCount() {
        return JpaHelper.GENRE.getPagesCount(repository.count());
    }

    @Override
    public int getArtistGenresPagesCount(long artistId) {
        return JpaHelper.GENRE.getPagesCount(repository.countByArtistsId(artistId));
    }

    @Override
    public int getTrackGenresPagesCount(long trackId) {
        return JpaHelper.GENRE.getPagesCount(repository.countByTracksId(trackId));
    }

    @Override
    public int getUserGenresPagesCount(long userId) {
        return JpaHelper.GENRE.getPagesCount(repository.countByUsersId(userId));
    }

    @Override
    public boolean checkGenreName(String name) {
        return repository.checkGenreName(name);
    }

}
