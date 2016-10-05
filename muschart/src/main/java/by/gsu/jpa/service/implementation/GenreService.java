package by.gsu.jpa.service.implementation;

import static by.gsu.constants.EntityConstants.ElementsCount.GenreCountElements.*;
import static by.gsu.constants.MessageConstants.TAKEN_GENRE_ERROR;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import by.gsu.bean.EntityIdAndName;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.repository.GenreRepository;
import by.gsu.jpa.service.dao.GenreServiceDAO;
import by.gsu.utility.JpaHelper;

public class GenreService implements GenreServiceDAO {

    @Autowired
    private GenreRepository repository;

    @Override
    public GenreEntity createGenre(final String name) throws ValidationException {
        GenreEntity genre = new GenreEntity(name);
        synchronized (GenreEntity.class) {
            if (!checkGenreName(name)) {
                return repository.save(genre);
            } else {
                throw new ValidationException(TAKEN_GENRE_ERROR);
            }
        }
    }

    @Override
    public void deleteGenreById(final long id) {
        repository.delete(id);
    }

    @Override
    public GenreEntity getGenreById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public Set<GenreEntity> getGenres(final int sort, final boolean order, final int page) {
        return new HashSet<>(repository.findAll(getPageable(sort, order, page)));
    }

    @Override
    public Set<GenreEntity> getArtistGenres(final long artistId, final int sort,
            final boolean order, final int page) {
        return new HashSet<>(repository.findByArtistsId(artistId, getPageable(sort, order, page)));
    }

    @Override
    public Set<GenreEntity> getTrackGenres(final long trackId, final int sort, final boolean order,
            final int page) {
        return new HashSet<>(repository.findByTracksId(trackId, getPageable(sort, order, page)));
    }

    @Override
    public Set<GenreEntity> getUserGenres(final long userId, final int sort, final boolean order,
            final int page) {
        return new HashSet<>(repository.findByUsersId(userId, getPageable(sort, order, page)));
    }

    @Override
    public Set<EntityIdAndName> getAllGenresIdAndName() {
        List<Object[]> objectsArray = repository.getAllGenresIdAndName();
        Set<EntityIdAndName> entitiesIdAndName = new HashSet<>(objectsArray.size());
        for (Object[] object : objectsArray) {
            EntityIdAndName unitBody = new EntityIdAndName((Long) object[0], (String) object[1]);
            entitiesIdAndName.add(unitBody);
        }
        return entitiesIdAndName;
    }

    @Override
    public long getGenresCount() {
        return repository.count();
    }

    @Override
    public long getArtistGenresCount(final long artistId) {
        return repository.countByArtistsId(artistId);
    }

    @Override
    public long getTrackGenresCount(final long trackId) {
        return repository.countByTracksId(trackId);
    }

    @Override
    public long getUserGenresCount(final long userId) {
        return repository.countByUsersId(userId);
    }

    @Override
    public boolean checkGenreName(final String name) {
        return repository.checkGenreName(name);
    }

    private Pageable getPageable(final int sort, final boolean order, final int page) {
        if (page > 0) {
            return new PageRequest(page - 1, GENRE_FULL_COUNT_ELEMENTS,
                    JpaHelper.getGenreSort(sort, order));
        } else {
            return new PageRequest(0, GENRE_COUNT_ELEMENTS, JpaHelper.getGenreSort(sort, order));
        }
    }

}
