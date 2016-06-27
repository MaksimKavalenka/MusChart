package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import by.gsu.bean.Artist;
import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.queries.ArtistDatabaseQueries;
import by.gsu.database.structure.columns.ArtistColumns;
import by.gsu.exception.ValidationException;

public class ArtistDatabaseEditor extends DatabaseEditor implements IArtistDAO {

    public ArtistDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void addArtist(final String name, final String photo) throws ValidationException {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setPhoto(photo);
        save(artist);
    }

    @Override
    public Artist getArtist(final int id) {
        return (Artist) session.get(Artist.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Artist> getArtists(final int idFrom, final int idTo) {
        return session.createCriteria(Artist.class)
                .add(Restrictions.between(ArtistColumns.ID.toString(), idFrom, idTo)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Artist> getAllArtists() {
        return session.createCriteria(Artist.class).list();
    }

    @Override
    public void deleteArtist(final int id) throws ValidationException {
        delete(getArtist(id));
    }

    @Override
    public void incRating(final int id) throws ValidationException {
        try {
            session.beginTransaction();
            ArtistDatabaseQueries.incRating(session, id).executeUpdate();
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
            ArtistDatabaseQueries.decRating(session, id).executeUpdate();
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
