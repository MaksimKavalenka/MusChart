package by.gsu.jpa.service.dao;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.TrackEntity;
import by.gsu.entity.UserEntity;
import by.gsu.exception.ValidationException;

public interface UserServiceDAO {

    UserEntity createUser(String login, String password, Set<GrantedAuthority> roles)
            throws ValidationException;

    UserEntity getUserByLogin(String login);

    void updateUserArtists(UserEntity user, ArtistEntity artist);

    void updateUserGenres(UserEntity user, GenreEntity genre);

    void updateUserTracks(UserEntity user, TrackEntity track);

    boolean checkLogin(String login);

}
