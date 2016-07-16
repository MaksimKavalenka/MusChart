package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.StructureConstants.TrackColumns;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import by.gsu.database.dao.ITrackDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Track;

public class TrackDatabaseEditor extends DatabaseEditor implements ITrackDAO {

    public TrackDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void createTrack(final String name, final String song, final String cover,
            final Date date) throws ValidationException {
        Track track = new Track();
        track.setName(name);
        track.setSong(song);
        track.setCover(cover);
        track.setDate(date);
        save(track);
    }

    @Override
    public Track getTrackById(final long id) {
        return (Track) session.get(Track.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Track> getTracksByIds(final long idFrom, final long idTo) {
        return session.createCriteria(Track.class)
                .add(Restrictions.between(TrackColumns.ID, idFrom, idTo)).list();
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
