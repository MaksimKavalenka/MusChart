package by.gsu.database.dao;

import by.gsu.exception.ValidationException;
import by.gsu.model.RoleModel;
import by.gsu.model.UserModel;

public interface UserDAO {

    void createUser(String login, String password, RoleModel role) throws ValidationException;

    UserModel getUser(String login, String password) throws ValidationException;

    UserModel getUserById(long id);

    UserModel getUserByLogin(String login);

}
