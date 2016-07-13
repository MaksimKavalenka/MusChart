package by.gsu.database.dao;

import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Genre;

public interface IGenreDAO extends IDAO {

    void addGenre(Genre genre) throws ValidationException;

    Genre getGenreById(long id) throws ValidationException;

    List<Genre> getGenresByIds(long idFrom, long idTo) throws ValidationException;

    List<Genre> getAllGenres() throws ValidationException;

    void deleteGenreById(long id) throws ValidationException;

    void incRating(long id) throws ValidationException;

    void decRating(long id) throws ValidationException;

}
