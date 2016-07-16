package by.gsu.database.dao;

import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;

public interface IArtistDAO extends IDAO {

    void createArtist(String name, String photo) throws ValidationException;

    Artist getArtistById(long id) throws ValidationException;

    List<Artist> getArtistsByIds(long idFrom, long idTo) throws ValidationException;

    List<Artist> getAllArtists() throws ValidationException;

    void deleteArtistById(long id) throws ValidationException;

    void incRating(long id) throws ValidationException;

    void decRating(long id) throws ValidationException;

}
