package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.StructureConstants.ArtistColumns;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import by.gsu.constants.StructureConstants;
import by.gsu.database.dao.IArtistDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;
import by.gsu.model.Genre;

public class ArtistDatabaseEditor extends DatabaseEditor implements IArtistDAO {

    public ArtistDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void createArtist(final String name, final String photo, final List<Genre> genres)
            throws ValidationException {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setPhoto(photo);
        artist.setGenres(genres);
        save(artist);
    }

    @Override
    public Artist getArtistById(final long id) {
        return (Artist) session.get(Artist.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Artist> getArtistsByIds(final long idFrom, final long idTo) {
        return session.createCriteria(Artist.class)
                .add(Restrictions.between(ArtistColumns.ID, idFrom, idTo)).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Artist> getAllArtists() {
        Criteria criteria = session.createCriteria(Artist.class);
        criteria.addOrder(Order.asc(StructureConstants.ArtistColumns.NAME));
        return criteria.list();
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

}
