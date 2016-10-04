package by.gsu.database.dao;

import java.util.List;

import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;

public interface ArtistDAO {

    ArtistEntity createArtist(String name, String photo, List<GenreEntity> genres);

    void deleteArtistById(long id);

    ArtistEntity getArtistById(long id);

    List<ArtistEntity> getAllArtists();

}
