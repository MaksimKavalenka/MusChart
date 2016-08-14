package by.gsu.database.dao;

import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Genre;

public interface IGenreDAO extends IDAO {

    void createGenre(String name) throws ValidationException;

    Genre getGenreById(long id) throws ValidationException;

    Genre getGenreByName(String name) throws ValidationException;

    List<Genre> getGenresByCriteria(int sort, boolean order, int page) throws ValidationException;

    List<Genre> getAllGenres() throws ValidationException;

    void deleteGenreById(long id) throws ValidationException;

}
