package by.gsu.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.gsu.entity.GenreEntity;

public interface GenreRepository extends CrudRepository<GenreEntity, Long> {

    @Query("FROM GenreEntity")
    List<GenreEntity> findAll(Pageable pageable);

    List<GenreEntity> findByArtistsId(long artistId, Pageable pageable);

    List<GenreEntity> findByTracksId(long trackId, Pageable pageable);

    List<GenreEntity> findByUsersId(long userId, Pageable pageable);

    @Query("SELECT genre.id, genre.name FROM GenreEntity genre")
    List<Object[]> getAllGenresIdAndName();

    long countByArtistsId(long artistId);

    long countByTracksId(long trackId);

    long countByUsersId(long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM GenreEntity WHERE name = ?1")
    boolean checkGenreName(String name);

}
