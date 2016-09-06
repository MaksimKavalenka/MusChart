package by.gsu.database.dao;

import by.gsu.exception.ValidationException;
import by.gsu.model.ArtistModel;
import by.gsu.model.GenreModel;
import by.gsu.model.RoleModel;
import by.gsu.model.TrackModel;
import by.gsu.model.UserModel;

public interface UserDAO {

    UserModel createUser(String login, String password, RoleModel role) throws ValidationException;

    UserModel getUserById(long id);

    UserModel authentication(String login, String password) throws ValidationException;

    void updateUserTracks(UserModel user, TrackModel track);

    void updateUserArtists(UserModel user, ArtistModel artist);

    void updateUserGenres(UserModel user, GenreModel genre);

    boolean checkLogin(String login);

}
