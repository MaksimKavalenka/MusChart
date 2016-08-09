package by.gsu.database.dao;

import java.util.Date;
import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;

public interface ITrackDAO extends IDAO {

    void createTrack(String songName, String song, String cover, String castName,
            List<Artist> artists, List<Genre> genres, Date date) throws ValidationException;

    Track getTrackById(long id) throws ValidationException;

    List<Track> getTracksByIdsAsc(long idFrom, long idTo) throws ValidationException;

    List<Track> getTracksByIdsDesc(long idFrom, long idTo) throws ValidationException;

    List<Track> getAllTracks() throws ValidationException;

    void deleteTrackById(long id) throws ValidationException;

    void incRating(long id) throws ValidationException;

    void decRating(long id) throws ValidationException;

}
