package by.gsu.database.dao;

import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.GenreModel;

public interface GenreDAO {

    GenreModel createGenre(String name) throws ValidationException;

    void deleteGenreById(long id);

    GenreModel getGenreById(long id);

    List<GenreModel> getGenresByCriteria(int sort, boolean order, int page);

    List<GenreModel> getArtistGenresByCriteria(long idArtist, int sort, boolean order, int page);

    List<GenreModel> getTrackGenresByCriteria(long idTrack, int sort, boolean order, int page);

    List<GenreModel> getUserGenresByCriteria(long idUser, int sort, boolean order, int page);

    List<GenreModel> getAllGenres();

    boolean checkName(String name);

}
