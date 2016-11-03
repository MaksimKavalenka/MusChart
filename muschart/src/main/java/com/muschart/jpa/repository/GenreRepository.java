package com.muschart.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.muschart.entity.GenreEntity;

public interface GenreRepository extends CrudRepository<GenreEntity, Long> {

    @Query("FROM GenreEntity")
    List<GenreEntity> findAll(Pageable pageable);

    List<GenreEntity> findByArtistsId(long artistId, Pageable pageable);

    List<GenreEntity> findByTracksId(long trackId, Pageable pageable);

    List<GenreEntity> findByUsersId(long userId, Pageable pageable);

    @Query("SELECT genre.id, genre.name FROM GenreEntity genre ORDER BY genre.name ASC")
    List<Object[]> getAllGenresIdAndName();

    @Modifying
    @Transactional
    @Query(value = "UPDATE genre SET genre.rating = genre.rating + 1 WHERE genre.id = ?1", nativeQuery = true)
    void setGenreLike(long genreId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE genre SET genre.rating = genre.rating - 1 WHERE genre.id = ?1", nativeQuery = true)
    void setGenreDislike(long genreId);

    long countByArtistsId(long artistId);

    long countByTracksId(long trackId);

    long countByUsersId(long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM GenreEntity WHERE name = ?1")
    boolean checkGenreName(String name);

}
