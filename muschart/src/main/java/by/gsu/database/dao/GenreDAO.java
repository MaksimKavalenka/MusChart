package by.gsu.database.dao;

import java.util.List;

import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;

public interface GenreDAO {

    GenreEntity createGenre(String name) throws ValidationException;

    void deleteGenreById(long id);

    GenreEntity getGenreById(long id);

    List<GenreEntity> getAllGenres();

    boolean checkName(String name);

}
