package by.gsu.database.editor;

import static by.gsu.constants.ExceptionConstants.AUTHORIZATION_ERROR;
import static by.gsu.constants.ExceptionConstants.TAKEN_LOGIN_ERROR;
import static by.gsu.constants.ModelStructureConstants.UserFields;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.UserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.RoleModel;
import by.gsu.model.TrackModel;
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
    public void updateUserArtists(final UserModel user, final ArtistModel artist) {
        List<ArtistModel> artists = user.getArtists();
        if (!artists.contains(artist)) {
            artists.add(artist);
        } else {
            artists.remove(artist);
        }
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    @Transactional
    public void updateUserGenres(final UserModel user, final GenreModel genre) {
        List<GenreModel> genres = user.getGenres();
        if (!genres.contains(genre)) {
            genres.add(genre);
        } else {
            genres.remove(genre);
        }
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    @Transactional
    public void updateUserTracks(final UserModel user, final TrackModel track) {
        List<TrackModel> tracks = user.getTracks();
        if (!tracks.contains(track)) {
            tracks.add(track);
        } else {
            tracks.remove(track);
        }
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    @Transactional
    public boolean checkLogin(final String login) {
        return getUniqueResultByCriteria(UserModel.class,
                Restrictions.eq(UserFields.LOGIN, login)) != null;
    }

}
