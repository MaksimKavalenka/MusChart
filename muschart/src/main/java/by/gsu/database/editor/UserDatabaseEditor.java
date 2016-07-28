package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.AUTHORIZATION_ERROR;
import static by.gsu.constants.ExceptionConstants.COMMIT_TRANSACTION_ERROR;
import static by.gsu.constants.ExceptionConstants.TAKEN_LOGIN_ERROR;
import static by.gsu.constants.StructureConstants.UserColumns;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.Role;
import by.gsu.model.User;

public class UserDatabaseEditor extends DatabaseEditor implements IUserDAO {

    public UserDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void createUser(final String login, final String password) throws ValidationException {
        User checkUser;
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(Role.USER);

        try {
            session.beginTransaction();
            checkUser = getUserByCriteria(Restrictions.eq(UserColumns.LOGIN, user.getLogin()));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new ValidationException(COMMIT_TRANSACTION_ERROR);
        }

        if (checkUser == null) {
            save(user);
        } else {
            throw new ValidationException(TAKEN_LOGIN_ERROR);
        }
    }

    @Override
    public User getUser(final String login, final String password) throws ValidationException {
        session.beginTransaction();
        User user = getUserByCriteria(Restrictions.eq(UserColumns.LOGIN, login),
                Restrictions.eq(UserColumns.PASSWORD, password));

        if (user != null) {
            return user;
        } else {
            throw new ValidationException(AUTHORIZATION_ERROR);
        }
    }

    @Override
    public User getUserById(final long id) {
        return (User) session.get(User.class, id);
    }

    @Override
    public User getUserByLogin(final String login) {
        session.beginTransaction();
        return getUserByCriteria(Restrictions.eq(UserColumns.LOGIN, login));
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
