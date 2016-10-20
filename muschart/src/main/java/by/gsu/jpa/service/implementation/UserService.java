package by.gsu.jpa.service.implementation;

import static by.gsu.constants.MessageConstants.TAKEN_LOGIN_ERROR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import by.gsu.entity.UserEntity;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.repository.ArtistRepository;
import by.gsu.jpa.repository.GenreRepository;
import by.gsu.jpa.repository.TrackRepository;
import by.gsu.jpa.repository.UserRepository;
import by.gsu.jpa.service.dao.UserServiceDAO;

public class UserService implements UserServiceDAO {

    @Autowired
    private UserRepository   repository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private GenreRepository  genreRepository;
    @Autowired
    private TrackRepository  trackRepository;

    @Override
    public UserEntity createUser(final String login, final String password,
            final List<GrantedAuthority> roles) throws ValidationException {
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
        if (!repository.isArtistLiked(userId, artistId)) {
            repository.addArtistToUser(userId, artistId);
            artistRepository.setArtistLike(artistId);
        } else {
            repository.deleteArtistFromUser(userId, artistId);
            artistRepository.setArtistDislike(artistId);
        }
    }

    @Override
    public void updateUserGenres(final long userId, final long genreId) {
        if (!repository.isGenreLiked(userId, genreId)) {
            repository.addGenreToUser(userId, genreId);
            genreRepository.setGenreLike(genreId);
        } else {
            repository.deleteGenreFromUser(userId, genreId);
            genreRepository.setGenreDislike(genreId);
        }
    }

    @Override
    public void updateUserTracks(final long userId, final long trackId) {
        if (!repository.isTrackLiked(userId, trackId)) {
            repository.addTrackToUser(userId, trackId);
            trackRepository.setTrackLike(trackId);
        } else {
            repository.deleteTrackFromUser(userId, trackId);
            trackRepository.setTrackDislike(trackId);
        }
    }

    @Override
    public boolean isArtistLiked(final long userId, final long artistId) {
        return repository.isArtistLiked(userId, artistId);
    }

    @Override
    public boolean isGenreLiked(final long userId, final long genreId) {
        return repository.isGenreLiked(userId, genreId);
    }

    @Override
    public boolean isTrackLiked(final long userId, final long trackId) {
        return repository.isTrackLiked(userId, trackId);
    }

    @Override
    public boolean checkLogin(final String login) {
        return repository.checkLogin(login);
    }

}
