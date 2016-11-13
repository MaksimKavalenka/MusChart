package com.muschart.service.impl;

import static com.muschart.constants.MessageConstants.TAKEN_LOGIN_MESSAGE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import com.muschart.entity.UserEntity;
import com.muschart.exception.ValidationException;
import com.muschart.jpa.repository.ArtistRepository;
import com.muschart.jpa.repository.GenreRepository;
import com.muschart.jpa.repository.TrackRepository;
import com.muschart.jpa.repository.UserRepository;
import com.muschart.service.dao.UserServiceDAO;

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
    public UserEntity createUser(String login, String password, List<GrantedAuthority> roles)
            throws ValidationException {
        UserEntity user = new UserEntity(login, password, roles);
        synchronized (UserService.class) {
            if (!checkLogin(login)) {
                return repository.save(user);
            } else {
                throw new ValidationException(TAKEN_LOGIN_MESSAGE);
            }
        }
    }

    @Override
    public UserEntity getUserByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public void updateUserArtists(long userId, long artistId) {
        if (!repository.isArtistLiked(userId, artistId)) {
            repository.addArtistToUser(userId, artistId);
            artistRepository.setArtistLike(artistId);
        } else {
            repository.deleteArtistFromUser(userId, artistId);
            artistRepository.setArtistDislike(artistId);
        }
    }

    @Override
    public void updateUserGenres(long userId, long genreId) {
        if (!repository.isGenreLiked(userId, genreId)) {
            repository.addGenreToUser(userId, genreId);
            genreRepository.setGenreLike(genreId);
        } else {
            repository.deleteGenreFromUser(userId, genreId);
            genreRepository.setGenreDislike(genreId);
        }
    }

    @Override
    public void updateUserTracks(long userId, long trackId) {
        if (!repository.isTrackLiked(userId, trackId)) {
            repository.addTrackToUser(userId, trackId);
            trackRepository.setTrackLike(trackId);
        } else {
            repository.deleteTrackFromUser(userId, trackId);
            trackRepository.setTrackDislike(trackId);
        }
    }

    @Override
    public boolean isArtistLiked(long userId, long artistId) {
        return repository.isArtistLiked(userId, artistId);
    }

    @Override
    public boolean isGenreLiked(long userId, long genreId) {
        return repository.isGenreLiked(userId, genreId);
    }

    @Override
    public boolean isTrackLiked(long userId, long trackId) {
        return repository.isTrackLiked(userId, trackId);
    }

    @Override
    public boolean checkLogin(String login) {
        return repository.checkLogin(login);
    }

}
