package com.muschart.service.client.dao;

public interface ArtistServiceDAO {

    void getArtists(int sort, boolean order, int page);

    void getGenreArtists(long genreId, int sort, boolean order, int page);

    void getUserArtists(int sort, boolean order, int page);

    void getPagesCount();

    void getGenreArtistsPagesCount(long genreId);

    void getUserPagesCount();

}