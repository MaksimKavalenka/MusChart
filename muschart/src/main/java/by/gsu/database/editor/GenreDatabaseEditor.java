package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import by.gsu.constants.StructureConstants;
import by.gsu.database.dao.IGenreDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Genre;

public class GenreDatabaseEditor extends DatabaseEditor implements IGenreDAO {

    public GenreDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void addGenre(final Genre genre) throws ValidationException {
        save(genre);
    }

    @Override
    public Genre getGenreById(final long id) {
        return (Genre) session.get(Genre.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Genre> getGenresByIds(final long idFrom, final long idTo) {
        return session.createCriteria(Genre.class)
                .add(Restrictions.between(StructureConstants.GenreColumns.ID, idFrom, idTo)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Genre> getAllGenres() {
        return session.createCriteria(Genre.class).list();
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

    @Override
    public void close() throws ValidationException {
        super.close();
    }

}
