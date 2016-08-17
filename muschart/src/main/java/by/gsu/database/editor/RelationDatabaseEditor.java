package by.gsu.database.editor;

import static by.gsu.constants.ModelStructureConstants.ArtistFields;
import static by.gsu.constants.ModelStructureConstants.GenreFields;
import static by.gsu.constants.ModelStructureConstants.RelationFields;
import static by.gsu.constants.ModelStructureConstants.TrackFields;

import java.util.List;

import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.dao.IRelationDAO;
import by.gsu.database.dao.ITrackDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.factory.TrackFactory;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;
import by.gsu.model.User;

public class RelationDatabaseEditor extends DatabaseEditor implements IRelationDAO {

    public RelationDatabaseEditor() throws ValidationException {
        super();
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
            case 2:
                return super.getElementsByCriteria(Artist.class, RelationFields.GENRES,
                        ArtistFields.NAME, idGenre, order, page);
            default:
                return null;
        }
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
            case 2:
                return super.getElementsByCriteria(Artist.class, RelationFields.TRACKS,
                        ArtistFields.NAME, idTrack, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Artist> getUserArtistsByCriteria(final long idUser, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Artist.class, RelationFields.USERS,
                        ArtistFields.ID, idUser, order, page);
            case 1:
                return super.getElementsByCriteria(Artist.class, RelationFields.USERS,
                        ArtistFields.RATING, idUser, order, page);
            case 2:
                return super.getElementsByCriteria(Artist.class, RelationFields.USERS,
                        ArtistFields.NAME, idUser, order, page);
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
            case 2:
                return super.getElementsByCriteria(Genre.class, RelationFields.ARTISTS,
                        GenreFields.NAME, idArtist, order, page);
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
            case 2:
                return super.getElementsByCriteria(Genre.class, RelationFields.TRACKS,
                        GenreFields.NAME, idTrack, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Genre> getUserGenresByCriteria(final long idUser, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Genre.class, RelationFields.USERS,
                        GenreFields.ID, idUser, order, page);
            case 1:
                return super.getElementsByCriteria(Genre.class, RelationFields.USERS,
                        GenreFields.RATING, idUser, order, page);
            case 2:
                return super.getElementsByCriteria(Genre.class, RelationFields.USERS,
                        GenreFields.NAME, idUser, order, page);
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
                        TrackFields.NAME, idArtist, order, page);
            case 3:
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
                        TrackFields.NAME, idGenre, order, page);
            case 3:
                return super.getElementsByCriteria(Track.class, RelationFields.GENRES,
                        TrackFields.RELEASE, idGenre, order, page);
            default:
                return null;
        }
    }

    @Override
    public List<Track> getUserTracksByCriteria(final long idUser, final int sort,
            final boolean order, final int page) throws ValidationException {
        switch (sort) {
            case 0:
                return super.getElementsByCriteria(Track.class, RelationFields.USERS,
                        TrackFields.ID, idUser, order, page);
            case 1:
                return super.getElementsByCriteria(Track.class, RelationFields.USERS,
                        TrackFields.RATING, idUser, order, page);
            case 2:
                return super.getElementsByCriteria(Track.class, RelationFields.USERS,
                        TrackFields.NAME, idUser, order, page);
            case 3:
                return super.getElementsByCriteria(Track.class, RelationFields.USERS,
                        TrackFields.RELEASE, idUser, order, page);
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
    public void updateUserArtists(final User user, final Artist artist) throws ValidationException {
        List<Artist> artists = user.getArtists();
        if (!artists.contains(artist)) {
            artists.add(artist);
        } else {
            artists.remove(artist);
        }
        update(user);
    }

    @Override
    public void updateUserGenres(final User user, final Genre genre) throws ValidationException {
        List<Genre> genres = user.getGenres();
        if (!genres.contains(genre)) {
            genres.add(genre);
        } else {
            genres.remove(genre);
        }
        update(user);
    }

    @Override
    public void updateUserTracks(final User user, final Track track) throws ValidationException {
        List<Track> tracks = user.getTracks();
        if (!tracks.contains(track)) {
            tracks.add(track);
        } else {
            tracks.remove(track);
        }
        update(user);
    }

}
