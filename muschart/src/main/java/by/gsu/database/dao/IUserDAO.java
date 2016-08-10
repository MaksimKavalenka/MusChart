package by.gsu.database.dao;

import by.gsu.exception.ValidationException;
import by.gsu.model.Role;
import by.gsu.model.User;

public interface IUserDAO extends IDAO {

    void createUser(String login, String password, Role role) throws ValidationException;

    User getUser(String login, String password) throws ValidationException;

    User getUserById(long id) throws ValidationException;

    User getUserByLogin(String login) throws ValidationException;

}
