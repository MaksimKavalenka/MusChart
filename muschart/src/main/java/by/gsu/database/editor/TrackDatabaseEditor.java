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
import by.gsu.model.Unit;

public class TrackDatabaseEditor extends DatabaseEditor implements ITrackDAO {

    public TrackDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void createTrack(final String songName, final String song, final String cover,
            final List<Unit> units, final List<Artist> artists, final List<Genre> genres,
            final Date release) throws ValidationException {
        Track track = new Track();
        track.setSongName(songName);
        track.setSong(song);
        track.setCover(cover);
        track.setUnits(units);
        track.setArtists(artists);
        track.setGenres(genres);
        track.setRelease(release);
        save(track);
    }

    @Override
    public Track getTrackById(final long id) {
        return (Track) session.get(Track.class, id);
    }

    @Override
    public List<Track> getTracksByCriteria(final int sort, final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElements(Track.class, TrackColumns.ID, order, page);
            case 1:
                return super.getElements(Track.class, TrackColumns.RATING, order, page);
            case 2:
                return super.getElements(Track.class, TrackColumns.RELEASE, order, page);
            default:
                return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
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

}
