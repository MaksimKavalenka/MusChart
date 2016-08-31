package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.GenreFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.constants.ModelStructureConstants;
import by.gsu.database.dao.GenreDAO;
import by.gsu.model.GenreModel;

public class GenreDatabaseEditor extends DatabaseEditor implements GenreDAO {

    public GenreDatabaseEditor() {
        super();
    }

    public GenreDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public void createGenre(final String name) {
        GenreModel genre = new GenreModel();
        genre.setName(name);
        sessionFactory.getCurrentSession().save(genre);
    }

    @Override
    @Transactional
    public GenreModel getGenreById(final long id) {
        return (GenreModel) sessionFactory.getCurrentSession().get(GenreModel.class, id);
    }

    @Override
    @Transactional
    public GenreModel getGenreByName(final String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GenreModel.class);
        criteria.add(Restrictions.eq(GenreFields.NAME, name));
        return (GenreModel) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public List<GenreModel> getGenresByCriteria(final int sort, final boolean order,
            final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(GenreModel.class, GenreFields.ID, order, page);
            case 1:
                return super.getElementsByCriteria(GenreModel.class, GenreFields.RATING, order,
                        page);
            case 2:
                return super.getElementsByCriteria(GenreModel.class, GenreFields.NAME, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<GenreModel> getAllGenres() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GenreModel.class);
        criteria.addOrder(Order.asc(ModelStructureConstants.GenreFields.NAME));
        return criteria.list();
    }

    @Override
    @Transactional
    public void deleteGenreById(final long id) {
        sessionFactory.getCurrentSession().delete(getGenreById(id));
    }

}
