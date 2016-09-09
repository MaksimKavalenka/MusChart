package by.gsu.database.dao;

import java.util.Date;
import java.util.List;

import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.TrackModel;
import by.gsu.model.UnitModel;

public interface TrackDAO {

    TrackModel createTrack(String name, String song, String cover, String video, Date release,
            List<UnitModel> units, List<ArtistModel> artists, List<GenreModel> genres);

    void deleteTrackById(long id);

    TrackModel getTrackById(long id);

    List<TrackModel> getAllTracks();

}
