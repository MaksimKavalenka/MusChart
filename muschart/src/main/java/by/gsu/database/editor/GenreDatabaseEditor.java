package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import by.gsu.bean.Genre;
import by.gsu.database.dao.IGenreDAO;
import by.gsu.database.queries.GenreDatabaseQueries;
import by.gsu.database.structure.columns.GenreColumns;
import by.gsu.exception.ValidationException;

public class GenreDatabaseEditor extends DatabaseEditor implements IGenreDAO {

    public GenreDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void addGenre(final String name) throws ValidationException {
        Genre tag = new Genre();
        tag.setName(name);
        save(tag);
    }

    @Override
    public Genre getGenre(final int id) {
        return (Genre) session.get(Genre.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Genre> getGenres(final int idFrom, final int idTo) {
        return session.createCriteria(Genre.class)
                .add(Restrictions.between(GenreColumns.ID.toString(), idFrom, idTo)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Genre> getAllGenres() {
        return session.createCriteria(Genre.class).list();
    }

    @Override
    public void deleteGenre(final int id) throws ValidationException {
        delete(getGenre(id));
    }

    @Override
    public void incRating(final int id) throws ValidationException {
        try {
            session.beginTransaction();
            GenreDatabaseQueries.incRating(session, id).executeUpdate();
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
            GenreDatabaseQueries.decRating(session, id).executeUpdate();
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
