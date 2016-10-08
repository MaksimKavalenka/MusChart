package by.gsu.jpa.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.bean.IdAndNameEntity;
import by.gsu.entity.ArtistEntity;
import by.gsu.jpa.repository.ArtistRepository;
import by.gsu.jpa.service.dao.ArtistServiceDAO;
import by.gsu.utility.JpaHelper;

public class ArtistService implements ArtistServiceDAO {

    @Autowired
    private ArtistRepository repository;

    @Override
    public ArtistEntity createArtist(final String name, final String photo,
            final List<Long> genresIds) {
        ArtistEntity artist = new ArtistEntity(name, photo);
        artist = repository.save(artist);
        for (long genreId : genresIds) {
            addGenreToArtist(artist.getId(), genreId);
        }
        return artist;
    }

    @Override
    public void addGenreToArtist(final long artistId, final long genreId) {
        repository.addGenreToArtist(artistId, genreId);
    }

    @Override
    public void deleteArtistById(final long id) {
        repository.delete(id);
    }

    @Override
    public ArtistEntity getArtistById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public List<ArtistEntity> getArtists(final int sort, final boolean order, final int page) {
        return repository.findAll(JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getGenreArtists(final long genreId, final int sort,
            final boolean order, final int page) {
        return repository.findByGenresId(genreId,
                JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getTrackArtists(final long trackId, final int sort,
            final boolean order, final int page) {
        return repository.findByTracksId(trackId,
                JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<ArtistEntity> getUserArtists(final long userId, final int sort, final boolean order,
            final int page) {
        return repository.findByUsersId(userId, JpaHelper.ARTIST.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameEntity> getAllArtistsIdAndName() {
        List<Object[]> objectsArray = repository.getAllArtistsIdAndName();
        List<IdAndNameEntity> idAndNameEntities = new ArrayList<>(objectsArray.size());
        for (Object[] object : objectsArray) {
            IdAndNameEntity artistIdAndName = new IdAndNameEntity((Long) object[0],
                    (String) object[1]);
            idAndNameEntities.add(artistIdAndName);
        }
        return idAndNameEntities;
    }

    @Override
    public int getArtistsPagesCount() {
        return JpaHelper.ARTIST.getPagesCount(repository.count());
    }

    @Override
    public int getGenreArtistsPagesCount(final long genreId) {
        return JpaHelper.ARTIST.getPagesCount(repository.countByGenresId(genreId));
    }

    @Override
    public int getTrackArtistsPagesCount(final long trackId) {
        return JpaHelper.ARTIST.getPagesCount(repository.countByTracksId(trackId));
    }

    @Override
    public int getUserArtistsPagesCount(final long userId) {
        return JpaHelper.ARTIST.getPagesCount(repository.countByUsersId(userId));
    }

}