package by.gsu.database.dao;

import by.gsu.bean.User;
import by.gsu.exception.ValidationException;

public interface IUserDAO extends IDAO {

    public void addUser(String login, String password) throws ValidationException;

    public User getUser(String login, String password) throws ValidationException;

    public User getUser(int id) throws ValidationException;

}
