package by.gsu.jpa.service.implementation;

import static by.gsu.constants.MessageConstants.TAKEN_LOGIN_ERROR;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.TrackEntity;
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
    public void updateUserArtists(final UserEntity user, final ArtistEntity artist) {
        Set<ArtistEntity> artists = repository.getUserArtists(user.getId());
        if (!artists.contains(artist)) {
            artists.add(artist);
        } else {
            artists.remove(artist);
        }
        user.setArtists(artists);
        repository.save(user);
    }

    @Override
    public void updateUserGenres(final UserEntity user, final GenreEntity genre) {
        Set<GenreEntity> genres = repository.getUserGenres(user.getId());
        if (!genres.contains(genre)) {
            genres.add(genre);
        } else {
            genres.remove(genre);
        }
        user.setGenres(genres);
        repository.save(user);
    }

    @Override
    public void updateUserTracks(final UserEntity user, final TrackEntity track) {
        Set<TrackEntity> tracks = repository.getUserTracks(user.getId());
        if (!tracks.contains(track)) {
            tracks.add(track);
        } else {
            tracks.remove(track);
        }
        user.setTracks(tracks);
        repository.save(user);
    }

    @Override
    public boolean checkLogin(final String login) {
        return repository.checkLogin(login);
    }

}
