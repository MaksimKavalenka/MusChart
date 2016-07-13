package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.AUTHORIZATION_ERROR;
import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.ExceptionConstants.DOUBLE_LOGIN_ERROR;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import by.gsu.constants.StructureConstants;
import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Role;
import by.gsu.model.User;

public class UserDatabaseEditor extends DatabaseEditor implements IUserDAO {

    public UserDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void addUser(final User user) throws ValidationException {
        User checkUser;
        user.setRole(Role.USER);

        try {
            session.beginTransaction();
            checkUser = getUserByCriteria(
                    Restrictions.eq(StructureConstants.UserColumns.LOGIN, user.getLogin()));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }

        if (checkUser == null) {
            save(user);
        } else {
            throw new ValidationException(DOUBLE_LOGIN_ERROR);
        }
    }

    @Override
    public User getUser(final String login, final String password) throws ValidationException {
        session.beginTransaction();
        User user = getUserByCriteria(Restrictions.eq(StructureConstants.UserColumns.LOGIN, login),
                Restrictions.eq(StructureConstants.UserColumns.PASSWORD, password));

        if (user != null) {
            return user;
        } else {
            throw new ValidationException(AUTHORIZATION_ERROR);
        }
    }

    @Override
    public User getUser(final long id) {
        return (User) session.get(User.class, id);
    }

    @Override
    public void close() throws ValidationException {
        super.close();
    }

    private User getUserByCriteria(final Criterion... criterions) {
        Criteria criteria = session.createCriteria(User.class);
        for (Criterion criterion : criterions) {
            criteria.add(criterion);
        }
        return (User) criteria.uniqueResult();
    }

}
