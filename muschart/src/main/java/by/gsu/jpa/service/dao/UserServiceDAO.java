package by.gsu.jpa.service.dao;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import by.gsu.entity.UserEntity;
import by.gsu.exception.ValidationException;

public interface UserServiceDAO {

    UserEntity createUser(String login, String password, List<GrantedAuthority> roles)
            throws ValidationException;

    UserEntity getUserByLogin(String login);

    void updateUserArtists(long userId, long artistId);

    void updateUserGenres(long userId, long genreId);

    void updateUserTracks(long userId, long trackId);

    boolean isArtistLiked(long userId, long artistId);

    boolean isGenreLiked(long userId, long genreId);

    boolean isTrackLiked(long userId, long trackId);

    boolean checkLogin(String login);

}
