package by.gsu.database.dao;

import java.util.Date;
import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;
import by.gsu.model.Unit;

public interface ITrackDAO extends IDAO {

    void createTrack(String songName, String song, String cover, List<Unit> units,
            List<Artist> artists, List<Genre> genres, Date release) throws ValidationException;

    Track getTrackById(long id) throws ValidationException;

    List<Track> getTracksByCriteria(int sort, boolean order, int page) throws ValidationException;

    List<Track> getAllTracks() throws ValidationException;

    void deleteTrackById(long id) throws ValidationException;

    void incRating(long id) throws ValidationException;

    void decRating(long id) throws ValidationException;

}
