package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import by.gsu.bean.Track;
import by.gsu.database.dao.ITrackDAO;
import by.gsu.database.queries.TrackDatabaseQueries;
import by.gsu.database.structure.columns.TrackColumns;
import by.gsu.exception.ValidationException;

public class TrackDatabaseEditor extends DatabaseEditor implements ITrackDAO {

    public TrackDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void addTrack(final String name, final String song, final String cover, final Date date)
            throws ValidationException {
        Track track = new Track();
        track.setName(name);
        track.setSong(song);
        track.setCover(cover);
        track.setDate(date);
        save(track);
    }

    @Override
    public Track getTrack(final int id) {
        return (Track) session.get(Track.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Track> getTracks(final int idFrom, final int idTo) {
        return session.createCriteria(Track.class)
                .add(Restrictions.between(TrackColumns.ID.toString(), idFrom, idTo)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Track> getAllTracks() {
        return session.createCriteria(Track.class).list();
    }

    @Override
    public void deleteTrack(final int id) throws ValidationException {
        delete(getTrack(id));
    }

    @Override
    public void incRating(final int id) throws ValidationException {
        try {
            session.beginTransaction();
            TrackDatabaseQueries.incRating(session, id).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

    @Override
    public void decRating(final int id) throws ValidationException {
        try {
            session.beginTransaction();
            TrackDatabaseQueries.decRating(session, id).executeUpdate();
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
