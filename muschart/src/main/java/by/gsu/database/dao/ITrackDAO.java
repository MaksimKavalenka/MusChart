package by.gsu.database.dao;

import java.util.Date;
import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Track;

public interface ITrackDAO extends IDAO {

    void createTrack(String name, String song, String cover, Date date) throws ValidationException;

    Track getTrackById(long id) throws ValidationException;

    List<Track> getTracksByIds(long idFrom, long idTo) throws ValidationException;

    List<Track> getAllTracks() throws ValidationException;

    void deleteTrackById(long id) throws ValidationException;

    void incRating(long id) throws ValidationException;

    void decRating(long id) throws ValidationException;

}
