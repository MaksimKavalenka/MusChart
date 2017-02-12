package com.muschart.service.database.impl;

import static com.muschart.constants.MessageConstants.TAKEN_GENRE_MESSAGE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.GenreEntity;
import com.muschart.exception.ValidationException;
import com.muschart.jpa.repository.GenreRepository;
import com.muschart.service.database.dao.GenreDatabaseServiceDAO;
import com.muschart.utility.JpaHelper;
import com.muschart.utility.Parser;

@Service("genreDatabaseService")
public class GenreDatabaseService implements GenreDatabaseServiceDAO {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public GenreEntity createGenre(String name) throws ValidationException {
        GenreEntity genre = new GenreEntity(name);
        synchronized (GenreEntity.class) {
            if (!checkGenreName(name)) {
                return genreRepository.save(genre);
            } else {
                throw new ValidationException(TAKEN_GENRE_MESSAGE);
            }
        }
    }

    @Override
    public void deleteGenreById(long id) {
        genreRepository.delete(id);
    }

    @Override
    public GenreEntity getGenreById(long id) {
        return genreRepository.findOne(id);
    }

    @Override
    public List<GenreEntity> getGenres(int sort, boolean order, int page) {
        return genreRepository.findAll(JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getArtistGenres(long artistId, int sort, boolean order, int page) {
        return genreRepository.findByArtistsId(artistId, JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getTrackGenres(long trackId, int sort, boolean order, int page) {
        return genreRepository.findByTracksId(trackId, JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getUserGenres(long userId, int sort, boolean order, int page) {
        return genreRepository.findByUsersId(userId, JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameDTO> getAllGenresIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(genreRepository.getAllGenresIdAndName());
    }

    @Override
    public int getGenresPagesCount() {
        return JpaHelper.GENRE.getPagesCount(genreRepository.count());
    }

    @Override
    public int getArtistGenresPagesCount(long artistId) {
        return JpaHelper.GENRE.getPagesCount(genreRepository.countByArtistsId(artistId));
    }

    @Override
    public int getTrackGenresPagesCount(long trackId) {
        return JpaHelper.GENRE.getPagesCount(genreRepository.countByTracksId(trackId));
    }

    @Override
    public int getUserGenresPagesCount(long userId) {
        return JpaHelper.GENRE.getPagesCount(genreRepository.countByUsersId(userId));
    }

    @Override
    public boolean checkGenreName(String name) {
        return genreRepository.checkGenreName(name);
    }

}