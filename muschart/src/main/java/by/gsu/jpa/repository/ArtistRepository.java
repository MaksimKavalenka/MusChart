package by.gsu.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.entity.ArtistEntity;

public interface ArtistRepository extends CrudRepository<ArtistEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO artist_genre(id_artist, id_genre) VALUES (?1, ?2)", nativeQuery = true)
    void addGenreToArtist(long artistId, long genreId);

    @Query("FROM ArtistEntity")
    List<ArtistEntity> findAll(Pageable pageable);

    List<ArtistEntity> findByGenresId(long genreId, Pageable pageable);

    List<ArtistEntity> findByUsersId(long userId, Pageable pageable);

    @Query("SELECT artist.id, artist.name FROM ArtistEntity artist ORDER BY artist.name ASC")
    List<Object[]> getAllArtistsIdAndName();

    @Query("SELECT artist.id, artist.name FROM TrackArtistEntity trackArtist JOIN trackArtist.track track JOIN trackArtist.artist artist WHERE track.id = ?1 ORDER BY trackArtist.id")
    List<Object[]> getTrackArtistsIdAndName(long trackId);

    long countByGenresId(long genreId);

    long countByUsersId(long userId);

}
