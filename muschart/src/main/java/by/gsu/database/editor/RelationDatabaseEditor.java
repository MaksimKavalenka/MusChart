package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.ArtistFields;
import static by.gsu.constants.ModelStructureConstants.GenreFields;
import static by.gsu.constants.ModelStructureConstants.RelationFields;
import static by.gsu.constants.ModelStructureConstants.TrackFields;

import java.util.List;

import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.dao.IRelationDAO;
import by.gsu.database.dao.ITrackDAO;
import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.factory.TrackFactory;
import by.gsu.factory.UserFactory;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;
import by.gsu.model.User;

public class RelationDatabaseEditor extends DatabaseEditor implements IRelationDAO {

    public RelationDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public List<Artist> getTrackArtistsByCriteria(final long idTrack, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Artist.class, RelationFields.TRACKS,
                        ArtistFields.ID, idTrack, order, page);
            case 1:
                return super.getElementsByCriteria(Artist.class, RelationFields.TRACKS,
                        ArtistFields.RATING, idTrack, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Artist> getGenreArtistsByCriteria(final long idGenre, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Artist.class, RelationFields.GENRES,
                        ArtistFields.ID, idGenre, order, page);
            case 1:
                return super.getElementsByCriteria(Artist.class, RelationFields.GENRES,
                        ArtistFields.RATING, idGenre, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Genre> getTrackGenresByCriteria(final long idTrack, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Genre.class, RelationFields.TRACKS,
                        GenreFields.ID, idTrack, order, page);
            case 1:
                return super.getElementsByCriteria(Genre.class, RelationFields.TRACKS,
                        GenreFields.RATING, idTrack, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Genre> getArtistGenresByCriteria(final long idArtist, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Genre.class, RelationFields.ARTISTS,
                        GenreFields.ID, idArtist, order, page);
            case 1:
                return super.getElementsByCriteria(Genre.class, RelationFields.ARTISTS,
                        GenreFields.RATING, idArtist, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Track> getArtistTracksByCriteria(final long idArtist, final int sort,
            final boolean order, final int page) {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Track.class, RelationFields.ARTISTS,
                        TrackFields.ID, idArtist, order, page);
            case 1:
                return super.getElementsByCriteria(Track.class, RelationFields.ARTISTS,
                        TrackFields.RATING, idArtist, order, page);
            case 2:
                return super.getElementsByCriteria(Track.class, RelationFields.ARTISTS,
                        TrackFields.RELEASE, idArtist, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Track> getGenreTracksByCriteria(final long idGenre, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Track.class, RelationFields.GENRES,
                        TrackFields.ID, idGenre, order, page);
            case 1:
                return super.getElementsByCriteria(Track.class, RelationFields.GENRES,
                        TrackFields.RATING, idGenre, order, page);
            case 2:
                return super.getElementsByCriteria(Track.class, RelationFields.GENRES,
                        TrackFields.RELEASE, idGenre, order, page);
            default:
                return null;
        }
    }

    @Override
    public void updateTrackArtists(final long idTrack, final List<Artist> artists)
            throws ValidationException {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            Track track = trackDAO.getTrackById(idTrack);
            track.setArtists(artists);
            save(track);
        }
    }

    @Override
    public void updateTrackGenres(final long idTrack, final List<Genre> genres)
            throws ValidationException {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            Track track = trackDAO.getTrackById(idTrack);
            track.setGenres(genres);
            save(track);
        }
    }

    @Override
    public void updateArtistGenres(final long idArtist, final List<Genre> genres)
            throws ValidationException {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            Artist artist = artistDAO.getArtistById(idArtist);
            artist.setGenres(genres);
            save(artist);
        }
    }

    @Override
    public void updateUserTracks(final long idUser, final List<Track> tracks)
            throws ValidationException {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUserById(idUser);
            user.setTracks(tracks);
            save(user);
        }
    }

    @Override
    public void updateUserArtists(final long idUser, final List<Artist> artists)
            throws ValidationException {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUserById(idUser);
            user.setArtists(artists);
            save(user);
        }
    }

    @Override
    public void updateUserGenres(final long idUser, final List<Genre> genres)
            throws ValidationException {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUserById(idUser);
            user.setGenres(genres);
            save(user);
        }
    }

}
