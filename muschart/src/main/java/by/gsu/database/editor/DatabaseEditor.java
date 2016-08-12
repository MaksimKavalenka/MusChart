package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.CLOSE_SESSION_ERROR;
import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.ExceptionConstants.OPEN_SESSION_ERROR;
import static by.gsu.constants.ModelStructureConstants.ModelFields;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import by.gsu.database.dao.IDAO;
import by.gsu.exception.ValidationException;
import by.gsu.hibernate.HibernateUtil;
import by.gsu.model.Model;

public abstract class DatabaseEditor implements IDAO {

    private static final int ELEMENT_COUNT = 15;

    protected Session        session;

    public DatabaseEditor() throws ValidationException {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (HibernateException e) {
            throw new ValidationException(OPEN_SESSION_ERROR);
        }
    }

    @Override
    public void close() throws ValidationException {
        try {
            session.close();
        } catch (HibernateException e) {
            throw new ValidationException(CLOSE_SESSION_ERROR);
        }
    }

    protected void save(final Object object) throws ValidationException {
        try {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

    protected void delete(final Object object) throws ValidationException {
        try {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String property, final boolean order, final int page) {
        int fromIndex = ELEMENT_COUNT * page - ELEMENT_COUNT;
        int toIndex = ELEMENT_COUNT * page;
        Criteria criteria = session.createCriteria(clazz);
        if (order) {
            criteria.addOrder(Order.asc(property));
        } else {
            criteria.addOrder(Order.desc(property));
        }
        if (criteria.list().size() >= toIndex) {
            return criteria.list().subList(fromIndex, toIndex);
        }
        if (criteria.list().size() > fromIndex) {
            return criteria.list().subList(fromIndex, criteria.list().size());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String searchProperty, final String sortProperty, final long id,
            final boolean order, final int page) {
        int fromIndex = ELEMENT_COUNT * page - ELEMENT_COUNT;
        int toIndex = ELEMENT_COUNT * page;
        Criteria criteria = session.createCriteria(clazz);
        criteria.createAlias(searchProperty, "alias");
        criteria.add(Restrictions.eq("alias" + "." + ModelFields.ID, id));
        if (order) {
            criteria.addOrder(Order.asc(sortProperty));
        } else {
            criteria.addOrder(Order.desc(sortProperty));
        }
        if (criteria.list().size() >= toIndex) {
            return criteria.list().subList(fromIndex, toIndex);
        }
        if (criteria.list().size() > fromIndex) {
            return criteria.list().subList(fromIndex, criteria.list().size());
        }
        return null;
    }

}
