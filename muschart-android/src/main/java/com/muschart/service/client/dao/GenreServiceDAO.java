package com.muschart.service.client.dao;

public interface GenreServiceDAO {

    void getGenres(int sort, boolean order, int page);

    void getUserGenres(int sort, boolean order, int page);

    void getPagesCount();

    void getUserPagesCount();

}