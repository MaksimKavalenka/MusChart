package by.gsu.database.dao;

import java.util.List;

import by.gsu.model.GenreModel;

public interface GenreDAO {

    void createGenre(String name);

    GenreModel getGenreById(long id);

    GenreModel getGenreByName(String name);

    List<GenreModel> getGenresByCriteria(int sort, boolean order, int page);

    List<GenreModel> getAllGenres();

    void deleteGenreById(long id);

}
