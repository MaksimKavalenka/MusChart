package by.gsu.database.dao;

import java.util.List;

import by.gsu.bean.Track;
import by.gsu.exception.ValidationException;

public interface ITrackDAO extends IDAO {

    void addTrack(String name, String song, String cover) throws ValidationException;

    Track getTrack(int id) throws ValidationException;

    List<Track> getTracks(int idFrom, int idTo) throws ValidationException;

    List<Track> getAllTracks() throws ValidationException;

    void deleteTrack(int id) throws ValidationException;

    void incRating(int id) throws ValidationException;

    void decRating(int id) throws ValidationException;

}
