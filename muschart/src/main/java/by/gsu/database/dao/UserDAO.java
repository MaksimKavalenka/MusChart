package by.gsu.database.dao;

import by.gsu.exception.ValidationException;
import by.gsu.model.RoleModel;
import by.gsu.model.UserModel;

public interface UserDAO {

    UserModel createUser(String login, String password, RoleModel role) throws ValidationException;

    UserModel getUserById(long id);

    UserModel authentication(String login, String password) throws ValidationException;

    boolean checkLogin(String login);

}
