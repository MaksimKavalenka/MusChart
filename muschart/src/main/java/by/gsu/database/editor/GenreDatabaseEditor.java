package by.gsu.database.editor;

import static by.gsu.constants.MessageConstants.TAKEN_GENRE_ERROR;
import static by.gsu.constants.ModelStructureConstants.GenreFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.constants.ModelStructureConstants;
import by.gsu.database.dao.GenreDAO;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;

public class GenreDatabaseEditor extends DatabaseEditor implements GenreDAO {

    public GenreDatabaseEditor() {
        super();
    }

    public GenreDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public GenreEntity createGenre(final String name) throws ValidationException {
        GenreEntity checkGenre = getUniqueResultByCriteria(GenreEntity.class,
                Restrictions.eq(GenreFields.NAME, name));
        if (checkGenre == null) {
            GenreEntity genre = new GenreEntity(name);
            sessionFactory.getCurrentSession().save(genre);
            return genre;
        } else {
            throw new ValidationException(TAKEN_GENRE_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteGenreById(final long id) {
        sessionFactory.getCurrentSession().delete(getGenreById(id));
    }

    @Override
    @Transactional
    public GenreEntity getGenreById(final long id) {
        return (GenreEntity) sessionFactory.getCurrentSession().get(GenreEntity.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<GenreEntity> getAllGenres() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GenreEntity.class);
        criteria.addOrder(Order.asc(ModelStructureConstants.GenreFields.NAME));
        return criteria.list();
    }

    @Override
    @Transactional
    public boolean checkName(final String name) {
        return getUniqueResultByCriteria(GenreEntity.class,
                Restrictions.eq(GenreFields.NAME, name)) != null;
    }

}
