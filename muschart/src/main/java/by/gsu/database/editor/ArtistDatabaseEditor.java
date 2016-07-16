package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.StructureConstants.ArtistColumns;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import by.gsu.database.dao.IArtistDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;

public class ArtistDatabaseEditor extends DatabaseEditor implements IArtistDAO {

    public ArtistDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void createArtist(final String name, final String photo) throws ValidationException {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setPhoto(photo);
        save(artist);
    }

    @Override
    public Artist getArtistById(final long id) {
        return (Artist) session.get(Artist.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Artist> getArtistsByIds(final long idFrom, final long idTo) {
        return session.createCriteria(Artist.class)
                .add(Restrictions.between(ArtistColumns.ID, idFrom, idTo)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Artist> getAllArtists() {
        return session.createCriteria(Artist.class).list();
    }

    @Override
    public void deleteArtistById(final long id) throws ValidationException {
        delete(getArtistById(id));
    }

    @Override
    public void incRating(final long id) throws ValidationException {
        try {
            session.beginTransaction();
            // ArtistDatabaseQueries.incRating(session, id).executeUpdate();
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
            // ArtistDatabaseQueries.decRating(session, id).executeUpdate();
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
