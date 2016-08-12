package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.ModelStructureConstants.GenreFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import by.gsu.constants.ModelStructureConstants;
import by.gsu.database.dao.IGenreDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Genre;

public class GenreDatabaseEditor extends DatabaseEditor implements IGenreDAO {

    public GenreDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void createGenre(final String name) throws ValidationException {
        Genre genre = new Genre();
        genre.setName(name);
        save(genre);
    }

    @Override
    public Genre getGenreById(final long id) {
        return (Genre) session.get(Genre.class, id);
    }

    @Override
    public Genre getGenreByName(final String name) {
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Genre.class);
        criteria.add(Restrictions.eq(GenreFields.NAME, name));
        return (Genre) criteria.uniqueResult();
    }

    @Override
    public List<Genre> getGenresByCriteria(final int sort, final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Genre.class, GenreFields.ID, order, page);
            case 1:
                return super.getElementsByCriteria(Genre.class, GenreFields.RATING, order, page);
            default:
                return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Genre> getAllGenres() {
        Criteria criteria = session.createCriteria(Genre.class);
        criteria.addOrder(Order.asc(ModelStructureConstants.GenreFields.NAME));
        return criteria.list();
    }

    @Override
    public void deleteGenreById(final long id) throws ValidationException {
        delete(getGenreById(id));
    }

    @Override
    public void incRating(final long id) throws ValidationException {
        try {
            session.beginTransaction();
            // GenreDatabaseQueries.incRating(session, id).executeUpdate();
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
            // GenreDatabaseQueries.decRating(session, id).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

}
