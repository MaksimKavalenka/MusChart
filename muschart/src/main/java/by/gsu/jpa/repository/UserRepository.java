package by.gsu.jpa.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_artist(id_user, id_artist) VALUES (?1, ?2)", nativeQuery = true)
    void setArtistLike(long userId, long artistId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_genre(id_user, id_genre) VALUES (?1, ?2)", nativeQuery = true)
    void setGenreLike(long userId, long genreId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_track(id_user, id_track) VALUES (?1, ?2)", nativeQuery = true)
    void setTrackLike(long userId, long trackId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_artist WHERE id_user = ?1 AND id_artist = ?2", nativeQuery = true)
    void setArtistDislike(long userId, long artistId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_artist WHERE id_user = ?1 AND id_genre = ?2", nativeQuery = true)
    void setGenreDislike(long userId, long genreId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_artist WHERE id_user = ?1 AND id_track = ?2", nativeQuery = true)
    void setTrackDislike(long userId, long trackId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity user JOIN user.artists userArtists WHERE userArtists.id = ?1")
    boolean isArtistLiked(long artistId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity user JOIN user.genres userGenres WHERE userGenres.id = ?1")
    boolean isGenreLiked(long genreId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity user JOIN user.tracks userTracks WHERE userTracks.id = ?1")
    boolean isTrackLiked(long trackId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity WHERE login = ?1")
    boolean checkLogin(String login);

}
