package by.gsu.jpa.service.dao;

import java.util.Set;

import by.gsu.bean.EntityIdAndName;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;

public interface GenreServiceDAO {

    GenreEntity createGenre(String name) throws ValidationException;

    void deleteGenreById(long id);

    GenreEntity getGenreById(long id);

    Set<GenreEntity> getGenres(int sort, boolean order, int page);

    Set<GenreEntity> getArtistGenres(long artistId, int sort, boolean order, int page);

    Set<GenreEntity> getTrackGenres(long trackId, int sort, boolean order, int page);

    Set<GenreEntity> getUserGenres(long userId, int sort, boolean order, int page);

    Set<EntityIdAndName> getAllGenresIdAndName();

    long getGenresCount();

    long getArtistGenresCount(long artistId);

    long getTrackGenresCount(long trackId);

    long getUserGenresCount(long userId);

    boolean checkGenreName(String name);

}
