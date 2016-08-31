package by.gsu.database.dao;

import java.util.List;

import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;

public interface ArtistDAO {

    void createArtist(String name, String photo, List<GenreModel> genres);

    ArtistModel getArtistById(long id);

    List<ArtistModel> getArtistsByCriteria(int sort, boolean order, int page);

    List<ArtistModel> getAllArtists();

    void deleteArtistById(long id);

}
