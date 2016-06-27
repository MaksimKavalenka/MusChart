package by.gsu.database.dao;

import java.util.List;

import by.gsu.bean.Artist;
import by.gsu.bean.Genre;
import by.gsu.bean.Track;
import by.gsu.exception.ValidationException;

public interface IRelationDAO extends IDAO {

    void updateTrackArtists(int idTrack, List<Artist> artists) throws ValidationException;

    void updateTrackGenres(int idTrack, List<Genre> genres) throws ValidationException;

    void updateArtistGenres(int idArtist, List<Genre> genres) throws ValidationException;

    void updateUserTracks(int idUser, List<Track> tracks) throws ValidationException;

    void updateUserArtists(int idUser, List<Artist> artists) throws ValidationException;

    void updateUserGenres(int idUser, List<Genre> genres) throws ValidationException;

}
