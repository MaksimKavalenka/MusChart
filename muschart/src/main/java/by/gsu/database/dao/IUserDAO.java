package by.gsu.database.dao;

import by.gsu.exception.ValidationException;
import by.gsu.model.User;

public interface IUserDAO extends IDAO {

    public void addUser(User user) throws ValidationException;

    public User getUser(String login, String password) throws ValidationException;

    public User getUser(long id) throws ValidationException;

}
