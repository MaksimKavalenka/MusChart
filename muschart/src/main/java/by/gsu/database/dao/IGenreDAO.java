package by.gsu.database.dao;

import java.util.List;

import by.gsu.bean.Genre;
import by.gsu.exception.ValidationException;

public interface IGenreDAO extends IDAO {

    void addGenre(String name) throws ValidationException;

    Genre getGenre(int id) throws ValidationException;

    List<Genre> getGenres(int idFrom, int idTo) throws ValidationException;

    List<Genre> getAllGenres() throws ValidationException;

    void deleteGenre(int id) throws ValidationException;

    void incRating(int id) throws ValidationException;

    void decRating(int id) throws ValidationException;

}
