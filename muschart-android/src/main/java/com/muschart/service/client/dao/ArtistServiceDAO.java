package com.muschart.service.client.dao;

public interface ArtistServiceDAO {

    void getArtists(int sort, boolean order, int page);

    void getUserArtists(int sort, boolean order, int page);

    void getPagesCount();

    void getUserPagesCount();

}