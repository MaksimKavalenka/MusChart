package com.muschart.service.client.dao;

public interface UserServiceDAO {

    void authentication(String login, String password);

    void logout();

}