package com.muschart.jpa.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.muschart.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_artist(id_user, id_artist) VALUES (?1, ?2)", nativeQuery = true)
    void addArtistToUser(long userId, long artistId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_genre(id_user, id_genre) VALUES (?1, ?2)", nativeQuery = true)
    void addGenreToUser(long userId, long genreId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_track(id_user, id_track) VALUES (?1, ?2)", nativeQuery = true)
    void addTrackToUser(long userId, long trackId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_artist WHERE id_user = ?1 AND id_artist = ?2", nativeQuery = true)
    void deleteArtistFromUser(long userId, long artistId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_genre WHERE id_user = ?1 AND id_genre = ?2", nativeQuery = true)
    void deleteGenreFromUser(long userId, long genreId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_track WHERE id_user = ?1 AND id_track = ?2", nativeQuery = true)
    void deleteTrackFromUser(long userId, long trackId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity user JOIN user.artists userArtists WHERE user.id = ?1 AND userArtists.id = ?2")
    boolean isArtistLiked(long userId, long artistId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity user JOIN user.genres userGenres WHERE user.id = ?1 AND userGenres.id = ?2")
    boolean isGenreLiked(long userId, long genreId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity user JOIN user.tracks userTracks WHERE user.id = ?1 AND userTracks.id = ?2")
    boolean isTrackLiked(long userId, long trackId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity WHERE login = ?1")
    boolean checkLogin(String login);

}
