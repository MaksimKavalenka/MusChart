package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.AUTHORIZATION_ERROR;
import static by.gsu.constants.ExceptionConstants.TAKEN_LOGIN_ERROR;
import static by.gsu.constants.ModelStructureConstants.UserFields;

import java.security.NoSuchAlgorithmException;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.UserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.RoleModel;
import by.gsu.model.UserModel;
import by.gsu.utility.SecureData;

public class UserDatabaseEditor extends DatabaseEditor implements UserDAO {

    public UserDatabaseEditor() {
        super();
    }

    public UserDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public UserModel createUser(final String login, final String password, final RoleModel role)
            throws ValidationException {
        try {
            UserModel checkUserLogin = getUniqueResultByCriteria(UserModel.class,
                    Restrictions.eq(UserFields.LOGIN, login));
            if (checkUserLogin == null) {
                UserModel user = new UserModel();
                user.setLogin(login);
                user.setPassword(SecureData.secureSha1(password));
                user.setRole(role);
                sessionFactory.getCurrentSession().save(user);
                return user;
            } else {
                throw new ValidationException(TAKEN_LOGIN_ERROR);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserModel getUserById(final long id) {
        return (UserModel) sessionFactory.getCurrentSession().get(UserModel.class, id);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public UserModel authentication(final String login, final String password)
            throws ValidationException {
        try {
            UserModel user = getUniqueResultByCriteria(UserModel.class,
                    Restrictions.eq(UserFields.LOGIN, login),
                    Restrictions.eq(UserFields.PASSWORD, SecureData.secureSha1(password)));
            if (user != null) {
                return user;
            } else {
                throw new ValidationException(AUTHORIZATION_ERROR);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean checkLogin(final String login) {
        return getUniqueResultByCriteria(UserModel.class,
                Restrictions.eq(UserFields.LOGIN, login)) != null;
    }

}
