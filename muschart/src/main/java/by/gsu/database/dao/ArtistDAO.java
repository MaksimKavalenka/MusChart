package by.gsu.database.dao;

import java.util.List;

import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;

public interface ArtistDAO {

    ArtistModel createArtist(String name, String photo, List<GenreModel> genres);

    void deleteArtistById(long id);

    ArtistModel getArtistById(long id);

    List<ArtistModel> getArtistsByCriteria(int sort, boolean order, int page);

    List<ArtistModel> getGenreArtistsByCriteria(long idGenre, int sort, boolean order, int page);

    List<ArtistModel> getTrackArtistsByCriteria(long idTrack, int sort, boolean order, int page);

    List<ArtistModel> getUserArtistsByCriteria(long idUser, int sort, boolean order, int page);

    List<ArtistModel> getAllArtists();

}
