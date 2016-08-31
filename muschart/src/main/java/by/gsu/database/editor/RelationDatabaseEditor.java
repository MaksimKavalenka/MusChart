package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.ArtistFields;
import static by.gsu.constants.ModelStructureConstants.GenreFields;
import static by.gsu.constants.ModelStructureConstants.RelationFields;
import static by.gsu.constants.ModelStructureConstants.TrackFields;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import by.gsu.database.dao.RelationDAO;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.TrackModel;
import by.gsu.model.UserModel;

public class RelationDatabaseEditor extends DatabaseEditor implements RelationDAO {

    public RelationDatabaseEditor() {
        super();
    }

    public RelationDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public List<ArtistModel> getGenreArtistsByCriteria(final long idGenre, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.GENRES,
                        ArtistFields.ID, idGenre, order, page);
            case 1:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.GENRES,
                        ArtistFields.RATING, idGenre, order, page);
            case 2:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.GENRES,
                        ArtistFields.NAME, idGenre, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<ArtistModel> getTrackArtistsByCriteria(final long idTrack, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.TRACKS,
                        ArtistFields.ID, idTrack, order, page);
            case 1:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.TRACKS,
                        ArtistFields.RATING, idTrack, order, page);
            case 2:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.TRACKS,
                        ArtistFields.NAME, idTrack, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<ArtistModel> getUserArtistsByCriteria(final long idUser, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.USERS,
                        ArtistFields.ID, idUser, order, page);
            case 1:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.USERS,
                        ArtistFields.RATING, idUser, order, page);
            case 2:
                return super.getElementsByCriteria(ArtistModel.class, RelationFields.USERS,
                        ArtistFields.NAME, idUser, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<GenreModel> getArtistGenresByCriteria(final long idArtist, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.ARTISTS,
                        GenreFields.ID, idArtist, order, page);
            case 1:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.ARTISTS,
                        GenreFields.RATING, idArtist, order, page);
            case 2:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.ARTISTS,
                        GenreFields.NAME, idArtist, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<GenreModel> getTrackGenresByCriteria(final long idTrack, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.TRACKS,
                        GenreFields.ID, idTrack, order, page);
            case 1:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.TRACKS,
                        GenreFields.RATING, idTrack, order, page);
            case 2:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.TRACKS,
                        GenreFields.NAME, idTrack, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<GenreModel> getUserGenresByCriteria(final long idUser, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.USERS,
                        GenreFields.ID, idUser, order, page);
            case 1:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.USERS,
                        GenreFields.RATING, idUser, order, page);
            case 2:
                return super.getElementsByCriteria(GenreModel.class, RelationFields.USERS,
                        GenreFields.NAME, idUser, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<TrackModel> getArtistTracksByCriteria(final long idArtist, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.ARTISTS,
                        TrackFields.ID, idArtist, order, page);
            case 1:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.ARTISTS,
                        TrackFields.RATING, idArtist, order, page);
            case 2:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.ARTISTS,
                        TrackFields.NAME, idArtist, order, page);
            case 3:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.ARTISTS,
                        TrackFields.RELEASE, idArtist, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<TrackModel> getGenreTracksByCriteria(final long idGenre, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.GENRES,
                        TrackFields.ID, idGenre, order, page);
            case 1:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.GENRES,
                        TrackFields.RATING, idGenre, order, page);
            case 2:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.GENRES,
                        TrackFields.NAME, idGenre, order, page);
            case 3:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.GENRES,
                        TrackFields.RELEASE, idGenre, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public List<TrackModel> getUserTracksByCriteria(final long idUser, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.USERS,
                        TrackFields.ID, idUser, order, page);
            case 1:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.USERS,
                        TrackFields.RATING, idUser, order, page);
            case 2:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.USERS,
                        TrackFields.NAME, idUser, order, page);
            case 3:
                return super.getElementsByCriteria(TrackModel.class, RelationFields.USERS,
                        TrackFields.RELEASE, idUser, order, page);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public void updateUserArtists(final UserModel user, final ArtistModel artist) {
        List<ArtistModel> artists = user.getArtists();
        if (!artists.contains(artist)) {
            artists.add(artist);
        } else {
            artists.remove(artist);
        }
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    @Transactional
    public void updateUserGenres(final UserModel user, final GenreModel genre) {
        List<GenreModel> genres = user.getGenres();
        if (!genres.contains(genre)) {
            genres.add(genre);
        } else {
            genres.remove(genre);
        }
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    @Transactional
    public void updateUserTracks(final UserModel user, final TrackModel track) {
        List<TrackModel> tracks = user.getTracks();
        if (!tracks.contains(track)) {
            tracks.add(track);
        } else {
            tracks.remove(track);
        }
        sessionFactory.getCurrentSession().update(user);
    }

}
