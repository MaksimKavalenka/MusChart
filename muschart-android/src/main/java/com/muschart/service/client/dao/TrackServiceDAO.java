package com.muschart.service.client.dao;

public interface TrackServiceDAO {

    void getTracks(int sort, boolean order, int page);

    void getArtistTracks(long artistId, int sort, boolean order, int page);

    void getUserTracks(int sort, boolean order, int page);

    void getPagesCount();

    void getArtistTracksPagesCount(long artistId);

    void getUserPagesCount();

}