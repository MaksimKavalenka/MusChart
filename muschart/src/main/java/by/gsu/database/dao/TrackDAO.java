package by.gsu.database.dao;

import java.util.Date;
import java.util.List;

import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.TrackEntity;
import by.gsu.entity.UnitEntity;

public interface TrackDAO {

    TrackEntity createTrack(String name, String song, String cover, String video, Date release,
            List<UnitEntity> units, List<ArtistEntity> artists, List<GenreEntity> genres);

    void deleteTrackById(long id);

    TrackEntity getTrackById(long id);

    List<TrackEntity> getAllTracks();

}
