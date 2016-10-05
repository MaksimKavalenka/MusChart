package by.gsu.jpa.service.implementation;

import static by.gsu.constants.MessageConstants.TAKEN_LOGIN_ERROR;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import by.gsu.entity.UserEntity;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.repository.UserRepository;
import by.gsu.jpa.service.dao.UserServiceDAO;

public class UserService implements UserServiceDAO {

    @Autowired
    private UserRepository repository;

    @Override
    public UserEntity createUser(final String login, final String password,
            final Set<GrantedAuthority> roles) throws ValidationException {
        UserEntity user = new UserEntity(login, password, roles);
        synchronized (UserService.class) {
            if (!checkLogin(login)) {
                return repository.save(user);
            } else {
                throw new ValidationException(TAKEN_LOGIN_ERROR);
            }
        }
    }

    @Override
    public UserEntity getUserByLogin(final String login) {
        return repository.findByLogin(login);
    }

    @Override
    public void updateUserArtists(final long userId, final long artistId) {
        if (!repository.isArtistLiked(artistId)) {
            repository.setArtistLike(userId, artistId);
        } else {
            repository.setArtistDislike(userId, artistId);
        }
    }

    @Override
    public void updateUserGenres(final long userId, final long genreId) {
        if (!repository.isGenreLiked(genreId)) {
            repository.setGenreLike(userId, genreId);
        } else {
            repository.setGenreDislike(userId, genreId);
        }
    }

    @Override
    public void updateUserTracks(final long userId, final long trackId) {
        if (!repository.isTrackLiked(trackId)) {
            repository.setTrackLike(userId, trackId);
        } else {
            repository.setTrackDislike(userId, trackId);
        }
    }

    @Override
    public boolean checkLogin(final String login) {
        return repository.checkLogin(login);
    }

}
