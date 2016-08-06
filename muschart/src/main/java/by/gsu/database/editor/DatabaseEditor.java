package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.CLOSE_SESSION_ERROR;
import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.ExceptionConstants.OPEN_SESSION_ERROR;

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

    protected Session session;

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

    public void save(final Object object) throws ValidationException {
        try {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

    public void delete(final Object object) throws ValidationException {
        try {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }
    }

    public <T extends Model> List<T> getElementsByAsc(final long idFrom, final long idTo,
            final Class<T> clazz, final String property) {
        return getElementsByOrder(idFrom, idTo, clazz, property, Order.asc(property));
    }

    public <T extends Model> List<T> getElementsByDesc(final long idFrom, final long idTo,
            final Class<T> clazz, final String property) {
        return getElementsByOrder(idFrom, idTo, clazz, property, Order.desc(property));
    }

    @SuppressWarnings("unchecked")
    private <T extends Model> List<T> getElementsByOrder(final long idFrom, final long idTo,
            final Class<T> clazz, final String property, final Order order) {
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.between(property, idFrom, idTo));
        criteria.addOrder(order);
        return criteria.list();
    }

}
