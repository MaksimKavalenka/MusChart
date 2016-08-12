package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.ModelStructureConstants.ArtistFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;

import by.gsu.constants.ModelStructureConstants;
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
    public List<Artist> getArtistsByCriteria(final int sort, final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Artist.class, ArtistFields.ID, order, page);
            case 1:
                return super.getElementsByCriteria(Artist.class, ArtistFields.RATING, order, page);
            default:
                return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Artist> getAllArtists() {
        Criteria criteria = session.createCriteria(Artist.class);
        criteria.addOrder(Order.asc(ModelStructureConstants.ArtistFields.NAME));
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
