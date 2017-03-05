package com.muschart.service.client.dao;

public interface TrackServiceDAO {

    void getTracks(int sort, boolean order, int page);

    void getPagesCount();

}