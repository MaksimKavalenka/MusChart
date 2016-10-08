package by.gsu.jpa.service.dao;

import java.util.List;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;

public interface GenreServiceDAO {

    GenreEntity createGenre(String name) throws ValidationException;

    void deleteGenreById(long id);

    GenreEntity getGenreById(long id);

    List<GenreEntity> getGenres(int sort, boolean order, int page);

    List<GenreEntity> getArtistGenres(long artistId, int sort, boolean order, int page);

    List<GenreEntity> getTrackGenres(long trackId, int sort, boolean order, int page);

    List<GenreEntity> getUserGenres(long userId, int sort, boolean order, int page);

    List<IdAndNameEntity> getAllGenresIdAndName();

    int getGenresPagesCount();

    int getArtistGenresPagesCount(long artistId);

    int getTrackGenresPagesCount(long trackId);

    int getUserGenresPagesCount(long userId);

    boolean checkGenreName(String name);

}
