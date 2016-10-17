package by.gsu.jpa.service.implementation;

import static by.gsu.constants.MessageConstants.TAKEN_GENRE_ERROR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.bean.entity.IdAndNameEntity;
import by.gsu.entity.GenreEntity;
import by.gsu.exception.ValidationException;
import by.gsu.jpa.repository.GenreRepository;
import by.gsu.jpa.service.dao.GenreServiceDAO;
import by.gsu.utility.JpaHelper;
import by.gsu.utility.Parser;

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
    public List<GenreEntity> getGenres(final int sort, final boolean order, final int page) {
        return repository.findAll(JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getArtistGenres(final long artistId, final int sort,
            final boolean order, final int page) {
        return repository.findByArtistsId(artistId,
                JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getTrackGenres(final long trackId, final int sort, final boolean order,
            final int page) {
        return repository.findByTracksId(trackId,
                JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<GenreEntity> getUserGenres(final long userId, final int sort, final boolean order,
            final int page) {
        return repository.findByUsersId(userId, JpaHelper.GENRE.getPageRequest(sort, order, page));
    }

    @Override
    public List<IdAndNameEntity> getAllGenresIdAndName() {
        return Parser.parseObjectsToIdAndNameEntities(repository.getAllGenresIdAndName());
    }

    @Override
    public int getGenresPagesCount() {
        return JpaHelper.GENRE.getPagesCount(repository.count());
    }

    @Override
    public int getArtistGenresPagesCount(final long artistId) {
        return JpaHelper.GENRE.getPagesCount(repository.countByArtistsId(artistId));
    }

    @Override
    public int getTrackGenresPagesCount(final long trackId) {
        return JpaHelper.GENRE.getPagesCount(repository.countByTracksId(trackId));
    }

    @Override
    public int getUserGenresPagesCount(final long userId) {
        return JpaHelper.GENRE.getPagesCount(repository.countByUsersId(userId));
    }

    @Override
    public boolean checkGenreName(final String name) {
        return repository.checkGenreName(name);
    }

}
