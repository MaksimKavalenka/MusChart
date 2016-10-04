package by.gsu.jpa.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.gsu.entity.ArtistEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.entity.TrackEntity;
import by.gsu.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    @Query("SELECT user.artists FROM UserEntity user WHERE user.id = ?1")
    Set<ArtistEntity> getUserArtists(long id);

    @Query("SELECT user.genres FROM UserEntity user WHERE user.id = ?1")
    Set<GenreEntity> getUserGenres(long id);

    @Query("SELECT user.tracks FROM UserEntity user WHERE user.id = ?1")
    Set<TrackEntity> getUserTracks(long id);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity WHERE login = ?1")
    boolean checkLogin(String login);

}
