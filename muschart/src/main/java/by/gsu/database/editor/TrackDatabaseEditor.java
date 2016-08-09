package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.StructureConstants.TrackColumns;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import by.gsu.database.dao.ITrackDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;

public class TrackDatabaseEditor extends DatabaseEditor implements ITrackDAO {

    public TrackDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void createTrack(final String songName, final String song, final String cover,
            final String castName, final List<Artist> artists, final List<Genre> genres,
            final Date date) throws ValidationException {
        Track track = new Track();
        track.setSongName(songName);
        track.setSong(song);
        track.setCover(cover);
        track.setCastName(castName);
        track.setArtists(artists);
        track.setGenres(genres);
        track.setDate(date);
        save(track);
    }

    @Override
    public Track getTrackById(final long id) {
        return (Track) session.get(Track.class, id);
    }

    @Override
    public List<Track> getTracksByIdsAsc(final long idFrom, final long idTo) {
        return super.getElementsByAsc(idFrom, idTo, Track.class, TrackColumns.ID);
    }

    @Override
    public List<Track> getTracksByIdsDesc(final long idFrom, final long idTo) {
        return super.getElementsByDesc(idFrom, idTo, Track.class, TrackColumns.ID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Track> getAllTracks() {
        return session.createCriteria(Track.class).list();
    }

    @Override
    public void deleteTrackById(final long id) throws ValidationException {
        delete(getTrackById(id));
    }

    @Override
    public void incRating(final long id) throws ValidationException {
        try {
            session.beginTransaction();
            // TrackDatabaseQueries.incRating(session, id).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

    @Override
    public void decRating(final long id) throws ValidationException {
        try {
            session.beginTransaction();
            // TrackDatabaseQueries.decRating(session, id).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

    @Override
    public void close() throws ValidationException {
        super.close();
    }

}
