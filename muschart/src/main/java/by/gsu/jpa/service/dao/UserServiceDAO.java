package by.gsu.jpa.service.dao;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import by.gsu.entity.UserEntity;
import by.gsu.exception.ValidationException;

public interface UserServiceDAO {

    UserEntity createUser(String login, String password, Set<GrantedAuthority> roles)
            throws ValidationException;

    UserEntity getUserByLogin(String login);

    void updateUserArtists(long userId, long artistId);

    void updateUserGenres(long userId, long genreId);

    void updateUserTracks(long userId, long trackId);

    boolean checkLogin(String login);

}
