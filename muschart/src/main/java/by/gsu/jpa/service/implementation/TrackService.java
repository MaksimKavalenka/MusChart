package by.gsu.jpa.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.entity.TrackEntity;
import by.gsu.jpa.repository.TrackRepository;
import by.gsu.jpa.service.dao.TrackServiceDAO;
import by.gsu.utility.JpaHelper;

public class TrackService implements TrackServiceDAO {

    @Autowired
    private TrackRepository repository;

    @Override
    public TrackEntity createTrack(final String name, final String song, final String cover,
            final String video, final Date release, final List<Long> artistsIds,
            final List<Long> unitsIds, final List<Long> genresIds) {
        TrackEntity track = new TrackEntity(name, song, cover, video, release);
        for (long artistId : artistsIds) {
            addArtistToTrack(track.getId(), artistId);
        }
        for (long unitId : unitsIds) {
            addUnitToTrack(track.getId(), unitId);
        }
        for (long genreId : genresIds) {
            addGenreToTrack(track.getId(), genreId);
        }
        return track;
    }

    @Override
    public void addArtistToTrack(final long trackId, final long artistId) {
        repository.addArtistToTrack(trackId, artistId);
    }

    @Override
    public void addGenreToTrack(final long trackId, final long genreId) {
        repository.addGenreToTrack(trackId, genreId);
    }

    @Override
    public void addUnitToTrack(final long trackId, final long unitId) {
        repository.addUnitToTrack(trackId, unitId);
    }

    @Override
    public void deleteTrackById(final long id) {
        repository.delete(id);
    }

    @Override
    public TrackEntity getTrackById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public List<TrackEntity> getTracks(final int sort, final boolean order, final int page) {
        return repository.findAll(JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getArtistTracks(final long artistId, final int sort,
            final boolean order, final int page) {
        return repository.findByArtistsId(artistId,
                JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getGenreTracks(final long genreId, final int sort, final boolean order,
            final int page) {
        return repository.findByGenresId(genreId,
                JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<TrackEntity> getUserTracks(final long userId, final int sort, final boolean order,
            final int page) {
        return repository.findByUsersId(userId, JpaHelper.TRACK.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameEntity> getAllTracksIdAndName() {
        List<Object[]> objectsArray = repository.getAllTracksIdAndName();
        List<IdAndNameEntity> idAndNameEntities = new ArrayList<>(objectsArray.size());
        for (Object[] object : objectsArray) {
            IdAndNameEntity trackIdAndName = new IdAndNameEntity((Long) object[0],
                    (String) object[1]);
            idAndNameEntities.add(trackIdAndName);
        }
        return idAndNameEntities;
    }

    @Override
    public int getTracksPagesCount() {
        return JpaHelper.TRACK.getPagesCount(repository.count());
    }

    @Override
    public int getArtistTracksPagesCount(final long artistId) {
        return JpaHelper.TRACK.getPagesCount(repository.countByArtistsId(artistId));
    }

    @Override
    public int getGenreTracksPagesCount(final long genreId) {
        return JpaHelper.TRACK.getPagesCount(repository.countByGenresId(genreId));
    }

    @Override
    public int getUserTracksPagesCount(final long userId) {
        return JpaHelper.TRACK.getPagesCount(repository.countByUsersId(userId));
    }

}
