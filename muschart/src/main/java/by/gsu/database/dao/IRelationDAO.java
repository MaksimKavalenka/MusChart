package by.gsu.database.dao;

import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;

public interface IRelationDAO extends IDAO {

    void updateTrackArtists(long idTrack, List<Artist> artists) throws ValidationException;

    void updateTrackGenres(long idTrack, List<Genre> genres) throws ValidationException;

    void updateArtistGenres(long idArtist, List<Genre> genres) throws ValidationException;

    void updateUserTracks(long idUser, List<Track> tracks) throws ValidationException;

    void updateUserArtists(long idUser, List<Artist> artists) throws ValidationException;

    void updateUserGenres(long idUser, List<Genre> genres) throws ValidationException;

}
