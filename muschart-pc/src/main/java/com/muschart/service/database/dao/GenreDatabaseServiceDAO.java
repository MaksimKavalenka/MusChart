package com.muschart.service.database.dao;

import java.util.List;

import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.GenreEntity;
import com.muschart.exception.ValidationException;

public interface GenreDatabaseServiceDAO {

    GenreEntity createGenre(String name) throws ValidationException;

    void deleteGenreById(long id);

    GenreEntity getGenreById(long id);

    List<GenreEntity> getGenres(int sort, boolean order, int page);

    List<GenreEntity> getArtistGenres(long artistId, int sort, boolean order, int page);

    List<GenreEntity> getTrackGenres(long trackId, int sort, boolean order, int page);

    List<GenreEntity> getUserGenres(long userId, int sort, boolean order, int page);

    List<IdAndNameDTO> getAllGenresIdAndName();

    int getGenresPagesCount();

    int getArtistGenresPagesCount(long artistId);

    int getTrackGenresPagesCount(long trackId);

    int getUserGenresPagesCount(long userId);

    boolean checkGenreName(String name);

}