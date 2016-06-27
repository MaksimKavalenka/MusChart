package by.gsu.database.editor;

import java.util.List;

import by.gsu.bean.Artist;
import by.gsu.bean.Genre;
import by.gsu.bean.Track;
import by.gsu.bean.User;
import by.gsu.database.dao.IArtistDAO;
import by.gsu.database.dao.IRelationDAO;
import by.gsu.database.dao.ITrackDAO;
import by.gsu.database.dao.IUserDAO;
import by.gsu.exception.ValidationException;
import by.gsu.factory.ArtistFactory;
import by.gsu.factory.TrackFactory;
import by.gsu.factory.UserFactory;

public class RelationDatabaseEditor extends DatabaseEditor implements IRelationDAO {

    public RelationDatabaseEditor() throws ValidationException {
        super();
    }

    @Override
    public void updateTrackArtists(final int idTrack, final List<Artist> artists)
            throws ValidationException {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            Track track = trackDAO.getTrack(idTrack);
            track.setArtists(artists);
            save(track);
        }
    }

    @Override
    public void updateTrackGenres(final int idTrack, final List<Genre> genres)
            throws ValidationException {
        try (ITrackDAO trackDAO = TrackFactory.getEditor()) {
            Track track = trackDAO.getTrack(idTrack);
            track.setGenres(genres);
            save(track);
        }
    }

    @Override
    public void updateArtistGenres(final int idArtist, final List<Genre> genres)
            throws ValidationException {
        try (IArtistDAO artistDAO = ArtistFactory.getEditor()) {
            Artist artist = artistDAO.getArtist(idArtist);
            artist.setGenres(genres);
            save(artist);
        }
    }

    @Override
    public void updateUserTracks(final int idUser, final List<Track> tracks)
            throws ValidationException {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUser(idUser);
            user.setTracks(tracks);
            save(user);
        }
    }

    @Override
    public void updateUserArtists(final int idUser, final List<Artist> artists)
            throws ValidationException {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUser(idUser);
            user.setArtists(artists);
            save(user);
        }
    }

    @Override
    public void updateUserGenres(final int idUser, final List<Genre> genres)
            throws ValidationException {
        try (IUserDAO userDAO = UserFactory.getEditor()) {
            User user = userDAO.getUser(idUser);
            user.setGenres(genres);
            save(user);
        }
    }

}
