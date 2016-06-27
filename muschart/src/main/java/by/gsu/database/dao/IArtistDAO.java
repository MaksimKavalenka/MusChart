package by.gsu.database.dao;

import java.util.List;

import by.gsu.bean.Artist;
import by.gsu.exception.ValidationException;

public interface IArtistDAO extends IDAO {

    void addArtist(String name, String photo) throws ValidationException;

    Artist getArtist(int id) throws ValidationException;

    List<Artist> getArtists(int idFrom, int idTo) throws ValidationException;

    List<Artist> getAllArtists() throws ValidationException;

    void deleteArtist(int id) throws ValidationException;

    void incRating(int id) throws ValidationException;

    void decRating(int id) throws ValidationException;

}
