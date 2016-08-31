package by.gsu.database.dao;

import java.util.List;

import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.TrackModel;
import by.gsu.model.UserModel;

public interface RelationDAO {

    List<ArtistModel> getGenreArtistsByCriteria(long idGenre, int sort, boolean order, int page);

    List<ArtistModel> getTrackArtistsByCriteria(long idTrack, int sort, boolean order, int page);

    List<ArtistModel> getUserArtistsByCriteria(long idUser, int sort, boolean order, int page);

    List<GenreModel> getArtistGenresByCriteria(long idArtist, int sort, boolean order, int page);

    List<GenreModel> getTrackGenresByCriteria(long idTrack, int sort, boolean order, int page);

    List<GenreModel> getUserGenresByCriteria(long idUser, int sort, boolean order, int page);

    List<TrackModel> getArtistTracksByCriteria(long idArtist, int sort, boolean order, int page);

    List<TrackModel> getGenreTracksByCriteria(long idGenre, int sort, boolean order, int page);

    List<TrackModel> getUserTracksByCriteria(long idUser, int sort, boolean order, int page);

    void updateUserTracks(UserModel user, TrackModel track);

    void updateUserArtists(UserModel user, ArtistModel artist);

    void updateUserGenres(UserModel user, GenreModel genre);

}
