package com.muschart.service.client.dao;

public interface GenreServiceDAO {

    void getGenres(int sort, boolean order, int page);

    void getPagesCount();

}